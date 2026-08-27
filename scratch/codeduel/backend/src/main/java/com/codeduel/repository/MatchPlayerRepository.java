package com.codeduel.repository;

import com.codeduel.model.MatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {
    Optional<MatchPlayer> findByMatchIdAndUserId(String matchId, Long userId);
    List<MatchPlayer> findByMatchId(String matchId);
    List<MatchPlayer> findByUserId(Long userId);
}
