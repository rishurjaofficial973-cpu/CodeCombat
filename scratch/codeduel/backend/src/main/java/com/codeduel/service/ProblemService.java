package com.codeduel.service;

import com.codeduel.dto.ProblemDto;
import com.codeduel.exception.ResourceNotFoundException;
import com.codeduel.model.Difficulty;
import com.codeduel.model.Problem;
import com.codeduel.model.UserProblemHistory;
import com.codeduel.repository.ProblemRepository;
import com.codeduel.repository.UserProblemHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProblemService {

    private static final Logger log = LoggerFactory.getLogger(ProblemService.class);

    private final ProblemRepository problemRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final Random random = new Random();

    public ProblemService(ProblemRepository problemRepository, UserProblemHistoryRepository userProblemHistoryRepository) {
        this.problemRepository = problemRepository;
        this.userProblemHistoryRepository = userProblemHistoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProblemDto> getFilteredProblems(Difficulty difficulty, String topic, String search, Pageable pageable, Long currentUserId) {
        String topicKeyword = topic;
        if (topic != null && !topic.isBlank() && !topic.equalsIgnoreCase("All")) {
            topicKeyword = mapTopicToKeyword(topic);
        } else {
            topicKeyword = null;
        }

        String searchKeyword = (search != null && !search.isBlank()) ? search.trim() : null;

        Page<Problem> problems = problemRepository.findFilteredProblems(difficulty, topicKeyword, searchKeyword, pageable);

        Set<String> solvedProblemIds = new HashSet<>();
        if (currentUserId != null) {
            solvedProblemIds.addAll(userProblemHistoryRepository.findSolvedProblemIdsByUserId(currentUserId));
        }

        return problems.map(p -> {
            ProblemDto dto = ProblemDto.fromEntity(p, false);
            dto.setIsSolvedByMe(solvedProblemIds.contains(p.getId()));
            return dto;
        });
    }

    private String mapTopicToKeyword(String topic) {
        String lower = topic.toLowerCase().trim();
        if (lower.contains("array") || lower.contains("hash")) return "Array";
        if (lower.contains("two pointer") || lower.contains("window")) return "Two Pointers";
        if (lower.contains("binary search")) return "Binary Search";
        if (lower.contains("string")) return "String";
        if (lower.contains("linked list")) return "Linked List";
        if (lower.contains("stack")) return "Stack";
        if (lower.contains("queue")) return "Queue";
        if (lower.contains("tree") || lower.contains("bst")) return "Tree";
        if (lower.contains("heap") || lower.contains("priority")) return "Heap";
        if (lower.contains("greedy")) return "Greedy";
        if (lower.contains("backtrack")) return "Backtracking";
        if (lower.contains("graph")) return "Graph";
        if (lower.contains("dynamic") || lower.contains("dp")) return "Dynamic Programming";
        if (lower.contains("bit")) return "Bit";
        if (lower.contains("trie")) return "Trie";
        if (lower.contains("advanced")) return "Segment Tree";
        return topic;
    }

    @Transactional(readOnly = true)
    public ProblemDto getProblemDtoById(String id, Long currentUserId) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found with id: " + id));

        ProblemDto dto = ProblemDto.fromEntity(problem, true);
        if (currentUserId != null) {
            Optional<UserProblemHistory> history = userProblemHistoryRepository.findByUserIdAndProblemId(currentUserId, id);
            history.ifPresent(uph -> {
                dto.setIsSolvedByMe(uph.getIsSolved());
                dto.setMyBestRuntimeMs(uph.getBestRuntimeMs());
            });
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public Problem selectProblemForMatch(Long player1Id, Long player2Id, Difficulty preferredDifficulty, String preferredTopic, int avgRating) {
        Difficulty targetDifficulty = preferredDifficulty;
        if (targetDifficulty == null) {
            if (avgRating < 1200) {
                targetDifficulty = Difficulty.EASY;
            } else if (avgRating <= 1650) {
                targetDifficulty = random.nextBoolean() ? Difficulty.EASY : Difficulty.MEDIUM;
            } else if (avgRating <= 2000) {
                targetDifficulty = Difficulty.MEDIUM;
            } else {
                targetDifficulty = random.nextBoolean() ? Difficulty.MEDIUM : Difficulty.HARD;
            }
        }

        List<String> played1 = player1Id != null ? userProblemHistoryRepository.findPlayedProblemIdsByUserId(player1Id) : Collections.emptyList();
        List<String> played2 = player2Id != null ? userProblemHistoryRepository.findPlayedProblemIdsByUserId(player2Id) : Collections.emptyList();
        Set<String> excludedIds = new HashSet<>(played1);
        excludedIds.addAll(played2);

        List<Problem> candidates;
        if (!excludedIds.isEmpty()) {
            candidates = problemRepository.findByDifficultyAndIdNotIn(targetDifficulty, new ArrayList<>(excludedIds));
            if (candidates.isEmpty()) {
                candidates = problemRepository.findByIdNotIn(new ArrayList<>(excludedIds));
            }
        } else {
            candidates = problemRepository.findByDifficultyAndIsActiveTrue(targetDifficulty);
        }

        if (candidates == null || candidates.isEmpty()) {
            candidates = problemRepository.findByDifficultyAndIsActiveTrue(targetDifficulty);
        }

        if (candidates.isEmpty()) {
            candidates = problemRepository.findAll();
        }

        if (candidates.isEmpty()) {
            throw new ResourceNotFoundException("No problems available in question bank.");
        }

        if (preferredTopic != null && !preferredTopic.isBlank() && !preferredTopic.equalsIgnoreCase("All")) {
            String keyword = mapTopicToKeyword(preferredTopic).toLowerCase();
            List<Problem> topicFiltered = candidates.stream()
                    .filter(p -> (p.getTopics() != null && p.getTopics().toLowerCase().contains(keyword))
                            || (p.getPatterns() != null && p.getPatterns().toLowerCase().contains(keyword)))
                    .toList();
            if (!topicFiltered.isEmpty()) {
                candidates = topicFiltered;
            }
        }

        return candidates.get(random.nextInt(candidates.size()));
    }
}
