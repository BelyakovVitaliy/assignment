package com.fuga.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "publishing_results")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishingResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "album_id", nullable = false)
	private Long albumId;

	@Enumerated(EnumType.STRING)
	@Column(name = "destination", nullable = false)
	private DestinationService destinationService;

	@Enumerated(EnumType.STRING)
	@Column(name = "result", nullable = false)
	private Result result;

	@Column(name = "message")
	private String message;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();


	public enum Result {
		ERROR,
		SUCCESS
	}
}
