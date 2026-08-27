package com.codeduel.repository;

import com.codeduel.model.Submission;
import com.codeduel.model.SubmissionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Page<Submission> findByUserIdOrderBySubmittedAtDesc(Long userId, Pageable pageable);
    List<Submission> findByMatchIdAndUserId(String matchId, Long userId);
    Optional<Submission> findFirstByMatchIdAndUserIdAndResultOrderBySubmittedAtAsc(String matchId, Long userId, SubmissionResult result);
    Page<Submission> findByProblemIdOrderBySubmittedAtDesc(String problemId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.user.id = :userId AND s.result = 'ACCEPTED'")
    Long countAcceptedByUserId(@Param("userId") Long userId);

    @Query("SELECT AVG(s.executionTimeMs) FROM Submission s WHERE s.problem.id = :problemId AND s.result = 'ACCEPTED'")
    Double getAvgExecutionTimeForProblem(@Param("problemId") String problemId);

    @Query("SELECT AVG(s.memoryUsageMb) FROM Submission s WHERE s.problem.id = :problemId AND s.result = 'ACCEPTED'")
    Double getAvgMemoryForProblem(@Param("problemId") String problemId);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.problem.id = :problemId AND s.result = 'ACCEPTED' AND s.executionTimeMs > :executionTimeMs")
    Long countSlowerAcceptedSubmissions(@Param("problemId") String problemId, @Param("executionTimeMs") Long executionTimeMs);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.problem.id = :problemId AND s.result = 'ACCEPTED'")
    Long countTotalAcceptedSubmissions(@Param("problemId") String problemId);
}
