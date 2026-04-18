package com.fuga.assignment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private AlbumStatus status = AlbumStatus.NEW;

	@Column(name = "image_src")
	private String imageSrc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artist_id", nullable = false)
	private Artist artist;

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Track> tracks = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "platforms", joinColumns = @JoinColumn(name = "album_id"))
	@Column(name = "platforms")
	@Enumerated(EnumType.STRING)
	@NotEmpty
	List<DestinationService> platforms = new ArrayList<>();

	public enum AlbumStatus {
		NEW,
		VALIDATED,
		PUBLISHED,
		DELETED
	}
}
