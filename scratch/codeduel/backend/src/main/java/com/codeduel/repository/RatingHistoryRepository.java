package com.codeduel.repository;

import com.codeduel.model.RatingHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingHistoryRepository extends JpaRepository<RatingHistory, Long> {
    List<RatingHistory> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    List<RatingHistory> findByUserIdOrderByCreatedAtAsc(Long userId);
}
