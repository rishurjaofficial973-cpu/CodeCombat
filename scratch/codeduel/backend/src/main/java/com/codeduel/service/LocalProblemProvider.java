package com.codeduel.service;

import com.codeduel.model.Difficulty;
import com.codeduel.model.Problem;
import com.codeduel.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service("localProblemProvider")
public class LocalProblemProvider implements ProblemProvider {

    private final ProblemRepository problemRepository;
    private final Random random = new Random();

    public LocalProblemProvider(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public List<Problem> getProblems() {
        return problemRepository.findAll();
    }

    @Override
    public Problem getRandomProblem() {
        List<Problem> problems = problemRepository.findAll();
        if (problems.isEmpty()) return null;
        return problems.get(random.nextInt(problems.size()));
    }

    @Override
    public Problem getProblemById(String id) {
        return problemRepository.findById(id).orElse(null);
    }

    @Override
    public List<Problem> getProblemsByDifficulty(String difficulty) {
        try {
            Difficulty diff = Difficulty.valueOf(difficulty.toUpperCase());
            return problemRepository.findByDifficultyAndIsActiveTrue(diff);
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Problem> getProblemsByTopic(String topic) {
        return problemRepository.findByTopicsContainingIgnoreCaseAndIsActiveTrue(topic);
    }
}
