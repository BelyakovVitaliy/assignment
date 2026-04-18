package com.fuga.assignment.repository;

import com.fuga.assignment.entity.DestinationService;
import com.fuga.assignment.entity.PublishingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishingResultRepository extends JpaRepository<PublishingResult, Long> {

    boolean existsByAlbumIdAndDestinationService(Long albumId, DestinationService destinationService);
}
