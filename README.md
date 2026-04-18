# Music Album Publication Service

This assignment emulates part of how Fuga handles music album publication.

The service exposes an API to create, update, delete, and retrieve albums. Before being published to content providers, each album must pass a set of validations. This implementation includes three: content (e.g. 18+), album cover, and genre validation. In this implementation the validators are stubs, but in a real system they could range from manual content review to third-party service delegation.

Once validation passes, the album is published to the destination platform(s) specified at creation time.

---

## Architecture

### Components

1. **[AlbumController.java](src/main/java/com/fuga/assignment/controller/AlbumController.java)**
   Delegates calls to `AlbumService`. The create endpoint saves the album to the DB and writes an `ALBUM_CREATED` outbox record. The get endpoint uses a Redis cache to reduce latency.

2. **[OutboxJob.java](src/main/java/com/fuga/assignment/job/OutboxJob.java)**
   Periodically scans the outbox table for unprocessed records and publishes Kafka messages accordingly. For example, an `ALBUM_CREATED` record triggers 3 validation messages — one per validation type.

3. **Validation Consumers**
   [ContentValidationConsumer.java](src/main/java/com/fuga/assignment/kafka/consumer/validation/ContentValidationConsumer.java),
   [GenreValidationConsumer.java](src/main/java/com/fuga/assignment/kafka/consumer/validation/GenreValidationConsumer.java),
   [ImageValidationConsumer.java](src/main/java/com/fuga/assignment/kafka/consumer/validation/ImageValidationConsumer.java)
   — each receives a validation message, performs the validation, and writes the result to the `validation_result` table.

4. **[AlbumStatusEvaluationJob.java](src/main/java/com/fuga/assignment/job/AlbumStatusEvaluationJob.java)**
   A periodic job that advances album status and notifies other components via the outbox. For example, it scans the `validation_result` table, finds `NEW` albums with all validations complete, and promotes them to `VALIDATED`. The same pattern applies to the publishing step.

5. **[SpotifyPublisher.java](src/main/java/com/fuga/assignment/kafka/consumer/publishing/SpotifyPublisher.java)**
   Consumes Kafka messages and publishes albums to Spotify via a Feign HTTP client — HTTP is used here because we need to know whether publication succeeded.

6. **[AlbumPublishedConsumer.java](src/main/java/com/fuga/assignment/kafka/consumer/other/AlbumPublishedConsumer.java)**
   Updates an artist's album count after an album is successfully published.

### Why Kafka?

- Decouples components from each other
- Guarantees message delivery via at-least-once semantics
- Improves scalability

---

## Running Locally

The project includes a `compose.yaml` that sets up the full environment. Start everything with:

```bash
docker compose up
```

To test the service, create an album:

```http
POST http://localhost:8080/api/v1/albums
Content-Type: application/json

{
  "name": "first album",
  "imageSrc": "/path/to/img",
  "artistId": 1,
  "tracks": [
    { "name": "first track", "src": "/path/to/track1" },
    { "name": "second track", "src": "/path/to/track2" }
  ],
  "destinations": ["SPOTIFY"]
}
```

After a short time, the album status will change to `PUBLISHED` and the artist with `id=1` will have their album count incremented.

---

## Running Tests

```bash
./mvnw test
```

---

## What I'd Do Differently

1. **More test coverage.** I implemented [SpotifyPublisherTest.java](src/test/java/com/fuga/assignment/unit/SpotifyPublisherTest.java), which covers all `SpotifyPublisher` use cases. Given more time, I would add similar unit tests for other components and a full component test covering the end-to-end flow using Testcontainers.

2. **Decouple `AlbumService` from the API layer.** Currently [AlbumService.java](src/main/java/com/fuga/assignment/service/AlbumService.java) is tightly coupled to the controller (the service uses api DTOs). Ideally there should be a middleLayer, which will adapt controller to the service.

3. **Complete the YouTube Music publisher.** [YTPublisher.java](src/main/java/com/fuga/assignment/kafka/consumer/publishing/YTPublisher.java) is a draft and exists to demonstrate the extendability of the service.
