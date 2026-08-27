package com.codeduel.repository;

import com.codeduel.model.Difficulty;
import com.codeduel.model.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, String> {
    Optional<Problem> findBySlug(String slug);
    List<Problem> findByDifficultyAndIsActiveTrue(Difficulty difficulty);
    List<Problem> findByTopicsContainingIgnoreCaseAndIsActiveTrue(String topic);
    List<Problem> findByPatternsContainingIgnoreCaseAndIsActiveTrue(String pattern);

    @Query("SELECT p FROM Problem p WHERE p.isActive = true AND " +
           "(:difficulty IS NULL OR p.difficulty = :difficulty) AND " +
           "(:topic IS NULL OR LOWER(p.topics) LIKE LOWER(CONCAT('%', :topic, '%')) OR LOWER(p.patterns) LIKE LOWER(CONCAT('%', :topic, '%')) OR LOWER(p.title) LIKE LOWER(CONCAT('%', :topic, '%'))) AND " +
           "(:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.id) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.topics) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Problem> findFilteredProblems(
            @Param("difficulty") Difficulty difficulty,
            @Param("topic") String topic,
            @Param("search") String search,
            Pageable pageable);

    @Query("SELECT p FROM Problem p WHERE p.isActive = true AND p.difficulty = :difficulty AND p.id NOT IN :excludedIds")
    List<Problem> findByDifficultyAndIdNotIn(
            @Param("difficulty") Difficulty difficulty,
            @Param("excludedIds") List<String> excludedIds);

    @Query("SELECT p FROM Problem p WHERE p.isActive = true AND p.id NOT IN :excludedIds")
    List<Problem> findByIdNotIn(@Param("excludedIds") List<String> excludedIds);

    Long countByDifficultyAndIsActiveTrue(Difficulty difficulty);
    Long countByIsActiveTrue();
}
