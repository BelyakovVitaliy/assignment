package com.fuga.assignment.repository;

import com.fuga.assignment.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findByStatus(Outbox.OutboxStatus status);
}
