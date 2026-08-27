package com.codeduel.service;

import com.codeduel.model.Difficulty;
import com.codeduel.model.Problem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Pluggable external problem provider.
 * Delegates to local repository fallback if external source is unreachable or unconfigured.
 */
@Service("externalProblemProvider")
public class ExternalProblemProvider implements ProblemProvider {

    private final ProblemProvider localProvider;

    public ExternalProblemProvider(@Qualifier("localProblemProvider") ProblemProvider localProvider) {
        this.localProvider = localProvider;
    }

    @Override
    public List<Problem> getProblems() {
        return localProvider.getProblems();
    }

    @Override
    public Problem getRandomProblem() {
        return localProvider.getRandomProblem();
    }

    @Override
    public Problem getProblemById(String id) {
        return localProvider.getProblemById(id);
    }

    @Override
    public List<Problem> getProblemsByDifficulty(String difficulty) {
        return localProvider.getProblemsByDifficulty(difficulty);
    }

    @Override
    public List<Problem> getProblemsByTopic(String topic) {
        return localProvider.getProblemsByTopic(topic);
    }
}
