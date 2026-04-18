package com.fuga.assignment.service;

import com.fuga.assignment.entity.Outbox;
import com.fuga.assignment.repository.OutboxRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OutboxService {

	private final OutboxRepository outboxRepository;
	private final Clock clock;


	@Transactional(propagation = Propagation.REQUIRED)
	public void createOutboxMessage(Outbox.OutboxType type, Long albumId) {
		Outbox outbox = Outbox.builder()
				.type(type)
				.albumId(albumId)
				.createdAt(LocalDateTime.now(clock))
				.status(Outbox.OutboxStatus.CREATED)
				.build();
		outboxRepository.save(outbox);
	}
}
