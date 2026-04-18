package com.fuga.assignment.repository;

import com.fuga.assignment.entity.ValidationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationResultRepository extends JpaRepository<ValidationResult, Long> {

    boolean existsByAlbumIdAndType(Long albumId, ValidationResult.ValidationType validationType);
}
