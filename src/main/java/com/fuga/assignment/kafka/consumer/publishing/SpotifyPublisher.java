package com.fuga.assignment.kafka.consumer.publishing;

import com.fuga.assignment.client.SpotifyClient;
import com.fuga.assignment.client.SpotifyPublishRequest;
import com.fuga.assignment.entity.Album;
import com.fuga.assignment.entity.DestinationService;
import com.fuga.assignment.entity.PublishingResult;
import com.fuga.assignment.exception.FugaException;
import com.fuga.assignment.kafka.KafkaTopics;
import com.fuga.assignment.kafka.message.AlbumPublishMessage;
import com.fuga.assignment.repository.AlbumRepository;
import com.fuga.assignment.repository.PublishingResultRepository;
import com.fuga.assignment.service.SerializationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpotifyPublisher {

	private final PublishingResultRepository publishingResultRepository;
	private final AlbumRepository albumRepository;
	private final SpotifyClient spotifyClient;
	private final SerializationHelper serializationHelper;
	private final Clock clock;

	@KafkaListener(topics = KafkaTopics.TOPIC_PUBLISH_SPOTIFY, groupId = "spotify-publisher-group")
	public void consume(String message) {
		AlbumPublishMessage event = serializationHelper.readValue(message, AlbumPublishMessage.class);
		log.info("Received album publish request for albumId={}", event.albumId());

		if (event.destinationService() != DestinationService.SPOTIFY) {
			throw new FugaException(FugaException.FugaExceptionCode.D004, String.format(
					"The event has wrong destination=%s, expected=%s. AlbumId=%s",
					event.destinationService(), DestinationService.SPOTIFY, event.albumId()));
		}

		if (publishingResultRepository.existsByAlbumIdAndDestinationService(event.albumId(), DestinationService.SPOTIFY)) {
			log.info("The album albumId={} was already published to {}", event.albumId(), DestinationService.SPOTIFY);
			return;
		}

		Album album = albumRepository.findById(event.albumId())
				.orElseThrow(() -> new FugaException(FugaException.FugaExceptionCode.D002));

		ResponseEntity<String> response = spotifyClient.publish(new SpotifyPublishRequest(album.getName(), album.getDescription()));
		if (response.getStatusCode().isError()) {
			throw new RuntimeException(String.format("Publishing to Spotify failed. Error: %s, albumId=%s", response.getBody(), album.getId()));
		}
		log.info("Pushed albumId={} to Spotify endpoint", event.albumId());

		publishingResultRepository.save(PublishingResult.builder()
				.albumId(event.albumId())
				.destinationService(DestinationService.SPOTIFY)
				.createdAt(LocalDateTime.now(clock))
				.result(PublishingResult.Result.SUCCESS)
				.build());

		log.info("The albumId={} was successfully published to {}", event.albumId(), DestinationService.SPOTIFY);
	}
}
