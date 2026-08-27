package com.codecombat.repository;

import com.codecombat.model.Match;
import com.codecombat.model.MatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
    List<Match> findByStatus(MatchStatus status);

    @Query("SELECT m FROM Match m JOIN m.matchPlayers mp WHERE mp.user.id = :userId ORDER BY m.createdAt DESC")
    Page<Match> findMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT m FROM Match m JOIN m.matchPlayers mp WHERE mp.user.id = :userId ORDER BY m.createdAt DESC")
    List<Match> findRecentMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.status = 'ACTIVE' AND m.endTime < :now")
    List<Match> findExpiredActiveMatches(@Param("now") LocalDateTime now);

    Long countByStatus(MatchStatus status);
}
