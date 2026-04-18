package com.fuga.assignment.kafka.consumer.validation;

import com.fuga.assignment.entity.ValidationResult;
import com.fuga.assignment.kafka.KafkaTopics;
import com.fuga.assignment.kafka.message.AlbumValidationMessage;
import com.fuga.assignment.repository.ValidationResultRepository;
import com.fuga.assignment.service.SerializationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageValidationConsumer {

	private final ValidationResultRepository validationResultRepository;
	private final SerializationHelper serializationHelper;
	private final Clock clock;

	@KafkaListener(topics = KafkaTopics.TOPIC_VALIDATE_IMAGE, groupId = "image-validation-group")
	public void consume(String message) {
		AlbumValidationMessage event = serializationHelper.readValue(message, AlbumValidationMessage.class);
		log.info("Received image validation request for albumId={}", event.albumId());

		if (validationResultRepository.existsByAlbumIdAndType(event.albumId(), ValidationResult.ValidationType.IMAGE)) {
			log.info("Image validation already recorded for albumId={}", event.albumId());
			return;
		}

		ValidationResult result = ValidationResult.builder()
				.albumId(event.albumId())
				.type(ValidationResult.ValidationType.IMAGE)
				.createdAt(LocalDateTime.now(clock))
				.result(ValidationResult.Result.SUCCESS)
				.build();
		validationResultRepository.save(result);

		log.info("Image validation SUCCESS saved for albumId={}", event.albumId());
	}
}
