package com.codeduel.service;

import com.codeduel.model.Problem;
import java.util.List;

public interface ProblemProvider {
    List<Problem> getProblems();
    Problem getRandomProblem();
    Problem getProblemById(String id);
    List<Problem> getProblemsByDifficulty(String difficulty);
    List<Problem> getProblemsByTopic(String topic);
}
