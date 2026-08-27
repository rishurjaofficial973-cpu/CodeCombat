package com.codecombat.repository;

import com.codecombat.model.UserProblemHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProblemHistoryRepository extends JpaRepository<UserProblemHistory, Long> {
    Optional<UserProblemHistory> findByUserIdAndProblemId(Long userId, String problemId);
    List<UserProblemHistory> findByUserIdAndIsSolvedTrue(Long userId);
    Page<UserProblemHistory> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT uph.problem.id FROM UserProblemHistory uph WHERE uph.user.id = :userId")
    List<String> findPlayedProblemIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT uph.problem.id FROM UserProblemHistory uph WHERE uph.user.id = :userId AND uph.isSolved = true")
    List<String> findSolvedProblemIdsByUserId(@Param("userId") Long userId);

    Long countByUserIdAndIsSolvedTrue(Long userId);
    Long countByUserId(Long userId);
}
