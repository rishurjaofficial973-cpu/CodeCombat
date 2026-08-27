package com.codeduel.service;

import com.codeduel.dto.ProblemDto;
import com.codeduel.model.Difficulty;
import com.codeduel.model.Problem;
import com.codeduel.model.User;
import com.codeduel.repository.ProblemRepository;
import com.codeduel.repository.UserProblemHistoryRepository;
import com.codeduel.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProblemRecommendationService {

    private final ProblemRepository problemRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final UserRepository userRepository;

    public static class RecommendationDto {
        private ProblemDto problem;
        private String reason;

        public RecommendationDto(ProblemDto problem, String reason) {
            this.problem = problem;
            this.reason = reason;
        }

        public ProblemDto getProblem() { return problem; }
        public String getReason() { return reason; }
    }

    public ProblemRecommendationService(ProblemRepository problemRepository,
                                        UserProblemHistoryRepository userProblemHistoryRepository,
                                        UserRepository userRepository) {
        this.problemRepository = problemRepository;
        this.userProblemHistoryRepository = userProblemHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<RecommendationDto> getRecommendationsForUser(Long userId) {
        List<RecommendationDto> recommendations = new ArrayList<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return recommendations;

        List<String> playedIds = userProblemHistoryRepository.findPlayedProblemIdsByUserId(userId);
        Set<String> playedSet = new HashSet<>(playedIds);

        int rating = user.getRating();
        Difficulty recommendedDiff = rating < 1400 ? Difficulty.EASY : (rating < 1800 ? Difficulty.MEDIUM : Difficulty.HARD);

        // Weak topics to target
        String[] targetTopics = {"Dynamic Programming", "Graphs", "Binary Search", "Two Pointers", "Trees & BST"};
        String[] reasons = {
                "Target your Dynamic Programming pattern mastery to boost your rating.",
                "Reinforce Graph BFS & DFS traversal fundamentals for upcoming duels.",
                "Sharpen your Binary Search range deduction speed under match pressure.",
                "Master Two Pointers & Sliding Window techniques for quick sub-2-minute solves.",
                "Practice Tree DP and recursion structures frequently used in competitive matches."
        };

        int idx = 0;
        for (String topic : targetTopics) {
            List<Problem> topicProblems = problemRepository.findByTopicsContainingIgnoreCaseAndIsActiveTrue(topic);
            for (Problem p : topicProblems) {
                if (!playedSet.contains(p.getId()) && (p.getDifficulty() == recommendedDiff || p.getDifficulty() == Difficulty.MEDIUM)) {
                    recommendations.add(new RecommendationDto(ProblemDto.fromEntity(p, false), reasons[idx % reasons.length]));
                    playedSet.add(p.getId());
                    break;
                }
            }
            idx++;
            if (recommendations.size() >= 4) break;
        }

        // If not enough, pick unplayed
        if (recommendations.size() < 3) {
            List<Problem> general = problemRepository.findByDifficultyAndIsActiveTrue(recommendedDiff);
            for (Problem p : general) {
                if (!playedSet.contains(p.getId())) {
                    recommendations.add(new RecommendationDto(ProblemDto.fromEntity(p, false), "Curated " + p.getDifficulty() + " problem to refine your match speed."));
                    playedSet.add(p.getId());
                    if (recommendations.size() >= 4) break;
                }
            }
        }

        return recommendations;
    }
}
