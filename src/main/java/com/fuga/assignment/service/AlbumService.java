package com.fuga.assignment.service;

import com.fuga.assignment.entity.Album;
import com.fuga.assignment.entity.Artist;
import com.fuga.assignment.entity.Outbox;
import com.fuga.assignment.exception.FugaException;
import com.fuga.assignment.generated.model.AlbumResponse;
import com.fuga.assignment.generated.model.CreateAlbumRequest;
import com.fuga.assignment.generated.model.UpdateAlbumRequest;
import com.fuga.assignment.mapper.AlbumMapper;
import com.fuga.assignment.repository.AlbumRepository;
import com.fuga.assignment.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fuga.assignment.exception.FugaException.FugaExceptionCode.D001;
import static com.fuga.assignment.exception.FugaException.FugaExceptionCode.D002;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

	private final AlbumRepository albumRepository;
	private final ArtistRepository artistRepository;
	private final AlbumMapper albumMapper;
	private final OutboxService outboxService;

	@Transactional
	public AlbumResponse createAlbum(CreateAlbumRequest request) {
		Artist artist = artistRepository.findById(request.getArtistId())
				.orElseThrow(() -> new FugaException(D001));

		Album album = albumMapper.toEntity(request, artist);
		Album saved = albumRepository.save(album);

		outboxService.createOutboxMessage(Outbox.OutboxType.ALBUM_CREATED, saved.getId());

		return albumMapper.toResponse(saved);
	}

	@CacheEvict(value = "albums", key = "#id")
	public AlbumResponse updateAlbum(Long id, UpdateAlbumRequest request) {
		Album album = albumRepository.findById(id)
				.orElseThrow(() -> new FugaException(D002));
		albumMapper.updateEntity(request, album);
		return albumMapper.toResponse(albumRepository.save(album));
	}

	@CacheEvict(value = "albums", key = "#id")
	public void deleteAlbum(Long id) {
		Album album = albumRepository.findById(id)
				.orElseThrow(() -> new FugaException(D002));
		if (album.getStatus() != Album.AlbumStatus.PUBLISHED) {
			throw new IllegalStateException("Only PUBLISHED album can be deleted");
		}
		album.setStatus(Album.AlbumStatus.DELETED);
		albumRepository.save(album);
	}

	@Cacheable(value = "albums", key = "#id")
	@Transactional(readOnly = true)
	public AlbumResponse getAlbum(Long id) {
		Album album = albumRepository.findById(id)
				.orElseThrow(() -> new FugaException(D002));
		return albumMapper.toResponse(album);
	}

	@Transactional(readOnly = true)
	public List<AlbumResponse> getAllAlbums() {
		return albumRepository.findAll().stream()
				.map(albumMapper::toResponse)
				.toList();
	}
}
