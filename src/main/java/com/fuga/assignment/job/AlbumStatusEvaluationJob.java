package com.fuga.assignment.job;

import com.fuga.assignment.entity.Album;
import com.fuga.assignment.entity.Outbox;
import com.fuga.assignment.entity.ValidationResult;
import com.fuga.assignment.repository.AlbumRepository;
import com.fuga.assignment.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlbumStatusEvaluationJob {

	private final AlbumRepository albumRepository;
	private final OutboxService outboxService;

	@Scheduled(fixedDelayString = "${cron-jobs.validation.fixed-delay-ms:10000}")
	@Transactional
	public void processValidatedAlbums() {
		evaluateValidatedAlbums();
		evaluatePublishedAlbums();
	}

	private void evaluateValidatedAlbums() {
		evaluateAlbums(
				() -> albumRepository.findByStatusWithAllValidationsCompleted(Album.AlbumStatus.NEW,
						ValidationResult.ValidationType.values().length), Album.AlbumStatus.VALIDATED, Outbox.OutboxType.ALBUM_VALIDATED
		);
	}

	private void evaluatePublishedAlbums() {
		evaluateAlbums(
				() -> albumRepository.findByStatusWithPublishingCompleted(Album.AlbumStatus.VALIDATED),
				Album.AlbumStatus.PUBLISHED, Outbox.OutboxType.ALBUM_PUBLISHED
		);
	}

	private void evaluateAlbums(Supplier<List<Album>> supplier, Album.AlbumStatus albumStatus, Outbox.OutboxType fireOutboxType) {
		List<Album> albums = supplier.get();
		if (albums.isEmpty()) {
			return;
		}
		log.info("Marking {} albums as {}", albums.size(), albumStatus);

		for (Album album : albums) {
			album.setStatus(albumStatus);
			albumRepository.save(album);
			if (fireOutboxType != null) {
				outboxService.createOutboxMessage(fireOutboxType, album.getId());
			}
			log.info("Album id={} name='{}' marked as {}", album.getId(), album.getName(), albumStatus);
		}
	}
}
