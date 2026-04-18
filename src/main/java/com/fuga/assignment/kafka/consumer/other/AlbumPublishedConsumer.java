package com.fuga.assignment.kafka.consumer.other;

import com.fuga.assignment.entity.Artist;
import com.fuga.assignment.exception.FugaException;
import com.fuga.assignment.generated.model.AlbumResponse;
import com.fuga.assignment.generated.model.ArtistResponse;
import com.fuga.assignment.kafka.KafkaTopics;
import com.fuga.assignment.kafka.message.AlbumPublishedMessage;
import com.fuga.assignment.repository.ArtistRepository;
import com.fuga.assignment.service.AlbumService;
import com.fuga.assignment.service.SerializationHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.fuga.assignment.exception.FugaException.FugaExceptionCode.D001;

@Component
@AllArgsConstructor
@Slf4j
public class AlbumPublishedConsumer {

	private final SerializationHelper serializationHelper;
	private final AlbumService albumService;
	private final ArtistRepository artistRepository;

	@KafkaListener(topics = KafkaTopics.TOPIC_ALBUM_PUBLISHED, groupId = "album-published-consumer-group")
	public void consume(String message) {
		AlbumPublishedMessage event = serializationHelper.readValue(message, AlbumPublishedMessage.class);
		log.info("Received album published message for albumId={}", event.albumId());
		AlbumResponse album = albumService.getAlbum(event.albumId());
		Artist artist = Optional.of(album.getArtist())
				.map(ArtistResponse::getId)
				.flatMap(artistRepository::findById)
				.orElseThrow(() -> new FugaException(D001));
		artist.setAlbumCount(artist.getAlbumCount() + 1);
		log.info("The amount of published albums was increased from {} to {} for artistId={}",
				artist.getAlbumCount() - 1, artist.getAlbumCount(), artist.getId());
	}

}
