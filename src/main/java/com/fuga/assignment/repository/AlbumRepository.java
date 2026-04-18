package com.fuga.assignment.repository;

import com.fuga.assignment.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

	@Query("""
			SELECT a FROM Album a
			WHERE a.status = :status
			AND (SELECT COUNT(DISTINCT vr.type)
			     FROM ValidationResult vr
			     WHERE vr.albumId = a.id
			     AND vr.result = 'SUCCESS') = :successValidationCount
			""")
	List<Album> findByStatusWithAllValidationsCompleted(Album.AlbumStatus status, int successValidationCount);


	@Query("""
			SELECT a FROM Album a
			WHERE a.status = :status
			AND (SELECT COUNT(DISTINCT vr.destinationService)
			     FROM PublishingResult vr
			     WHERE vr.albumId = a.id
			     AND vr.result = 'SUCCESS') = SIZE(a.platforms)
			""")
	List<Album> findByStatusWithPublishingCompleted(Album.AlbumStatus status);

}
