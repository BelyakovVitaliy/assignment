package com.fuga.assignment.job;

import com.fuga.assignment.entity.DestinationService;
import com.fuga.assignment.entity.Outbox;
import com.fuga.assignment.generated.model.AlbumDestinations;
import com.fuga.assignment.kafka.KafkaTopics;
import com.fuga.assignment.kafka.message.AlbumPublishMessage;
import com.fuga.assignment.kafka.message.AlbumPublishedMessage;
import com.fuga.assignment.kafka.message.AlbumValidationMessage;
import com.fuga.assignment.repository.OutboxRepository;
import com.fuga.assignment.service.AlbumService;
import com.fuga.assignment.service.SerializationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxJob {

	private final OutboxRepository outboxRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final SerializationHelper serializationHelper;
	private final AlbumService albumService;

	@Scheduled(fixedDelayString = "${cron-jobs.outbox.fixed-delay-ms:5000}")
	@Transactional
	public void processOutbox() {
		List<Outbox> pending = outboxRepository.findByStatus(Outbox.OutboxStatus.CREATED);
		if (pending.isEmpty()) {
			return;
		}
		log.info("Processing {} pending events", pending.size());

		for (Outbox event : pending) {
			switch (event.getType()) {
				case ALBUM_CREATED -> processValidation(event);
				case ALBUM_VALIDATED -> processPublishing(event);
				case ALBUM_PUBLISHED -> processPublishNotification(event);
			}
		}
	}

	private void processPublishNotification(Outbox event) {
		String payload = serializationHelper.serializeAsString(new AlbumPublishedMessage(event.getAlbumId()));
		kafkaTemplate.send(KafkaTopics.TOPIC_ALBUM_PUBLISHED, String.valueOf(event.getAlbumId()), payload);
		event.setStatus(Outbox.OutboxStatus.SENT);
		outboxRepository.save(event);
		log.info("Sent published messages for albumId={}, outboxId={}", event.getAlbumId(), event.getId());
	}

	private void processPublishing(Outbox event) {
		List<AlbumDestinations> destinations = albumService.getAlbum(event.getAlbumId()).getDestinations();
		destinations.forEach(destination -> {
			switch (destination) {
				case SPOTIFY -> {
					String payload = serializationHelper.serializeAsString(new AlbumPublishMessage(event.getAlbumId(), DestinationService.SPOTIFY));
					kafkaTemplate.send(KafkaTopics.TOPIC_PUBLISH_SPOTIFY, String.valueOf(event.getAlbumId()), payload);
				}
				// TODO other destinations
			}
		});
		event.setStatus(Outbox.OutboxStatus.SENT);
		outboxRepository.save(event);
		log.info("Sent publishing messages for albumId={}, outboxId={}", event.getAlbumId(), event.getId());
	}

	private void processValidation(Outbox event) {
		String payload = serializationHelper.serializeAsString(new AlbumValidationMessage(event.getAlbumId()));

		KafkaTopics.VALIDATION_TOPICS
				.forEach(topic -> kafkaTemplate.send(topic, String.valueOf(event.getAlbumId()), payload));
		event.setStatus(Outbox.OutboxStatus.SENT);
		outboxRepository.save(event);
		log.info("Sent validation messages for albumId={}, outboxId={}", event.getAlbumId(), event.getId());
	}
}
