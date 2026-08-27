package com.codeduel.service;

import com.codeduel.dto.AdminStatsDto;
import com.codeduel.dto.ProblemDto;
import com.codeduel.dto.UserDto;
import com.codeduel.exception.ResourceNotFoundException;
import com.codeduel.model.*;
import com.codeduel.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final MatchRepository matchRepository;
    private final SubmissionRepository submissionRepository;
    private final TestCaseRepository testCaseRepository;

    public AdminService(UserRepository userRepository,
                        ProblemRepository problemRepository,
                        MatchRepository matchRepository,
                        SubmissionRepository submissionRepository,
                        TestCaseRepository testCaseRepository) {
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.matchRepository = matchRepository;
        this.submissionRepository = submissionRepository;
        this.testCaseRepository = testCaseRepository;
    }

    @Transactional(readOnly = true)
    public AdminStatsDto getAdminStats() {
        AdminStatsDto stats = new AdminStatsDto();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalMatches(matchRepository.count());
        stats.setTotalProblems(problemRepository.count());
        stats.setTotalSubmissions(submissionRepository.count());
        stats.setActiveMatches(matchRepository.countByStatus(MatchStatus.ACTIVE));
        stats.setEasyProblems(problemRepository.countByDifficultyAndIsActiveTrue(Difficulty.EASY));
        stats.setMediumProblems(problemRepository.countByDifficultyAndIsActiveTrue(Difficulty.MEDIUM));
        stats.setHardProblems(problemRepository.countByDifficultyAndIsActiveTrue(Difficulty.HARD));
        return stats;
    }

    @Transactional
    public ProblemDto createProblem(ProblemDto dto) {
        Problem p = new Problem();
        p.setId(dto.getId() != null ? dto.getId() : "CD-" + String.format("%04d", problemRepository.count() + 1));
        p.setTitle(dto.getTitle());
        p.setSlug(dto.getSlug() != null ? dto.getSlug() : dto.getTitle().toLowerCase().replaceAll("[^a-z0-9]+", "-"));
        p.setDifficulty(dto.getDifficulty() != null ? dto.getDifficulty() : Difficulty.MEDIUM);
        p.setTopics(dto.getTopics());
        p.setPatterns(dto.getPatterns());
        p.setDescription(dto.getDescription());
        p.setConstraints(dto.getConstraints());
        p.setExamples(dto.getExamples());
        p.setInputFormat(dto.getInputFormat());
        p.setOutputFormat(dto.getOutputFormat());
        p.setTimeLimitMs(dto.getTimeLimitMs() != null ? dto.getTimeLimitMs() : 2000);
        p.setMemoryLimitMb(dto.getMemoryLimitMb() != null ? dto.getMemoryLimitMb() : 256);
        p.setExpectedTimeComplexity(dto.getExpectedTimeComplexity());
        p.setExpectedSpaceComplexity(dto.getExpectedSpaceComplexity());
        p.setSource(dto.getSource() != null ? dto.getSource() : "Admin Created");
        p.setExternalUrl(dto.getExternalUrl());
        p.setIsActive(true);

        if (dto.getStarterCodes() != null) {
            p.setStarterCodeJava(dto.getStarterCodes().get("JAVA"));
            p.setStarterCodePython(dto.getStarterCodes().get("PYTHON"));
            p.setStarterCodeCpp(dto.getStarterCodes().get("CPP"));
            p.setStarterCodeJs(dto.getStarterCodes().get("JAVASCRIPT"));
        }

        p = problemRepository.save(p);
        return ProblemDto.fromEntity(p, true);
    }

    @Transactional
    public ProblemDto updateProblem(String id, ProblemDto dto) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found with id: " + id));

        if (dto.getTitle() != null) p.setTitle(dto.getTitle());
        if (dto.getDifficulty() != null) p.setDifficulty(dto.getDifficulty());
        if (dto.getTopics() != null) p.setTopics(dto.getTopics());
        if (dto.getPatterns() != null) p.setPatterns(dto.getPatterns());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getConstraints() != null) p.setConstraints(dto.getConstraints());
        if (dto.getTimeLimitMs() != null) p.setTimeLimitMs(dto.getTimeLimitMs());
        if (dto.getMemoryLimitMb() != null) p.setMemoryLimitMb(dto.getMemoryLimitMb());
        if (dto.getExpectedTimeComplexity() != null) p.setExpectedTimeComplexity(dto.getExpectedTimeComplexity());
        if (dto.getExpectedSpaceComplexity() != null) p.setExpectedSpaceComplexity(dto.getExpectedSpaceComplexity());

        p = problemRepository.save(p);
        return ProblemDto.fromEntity(p, true);
    }

    @Transactional
    public void deleteProblem(String id) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found with id: " + id));
        p.setIsActive(false);
        problemRepository.save(p);
    }

    @Transactional
    public void toggleUserBan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setIsBanned(!Boolean.TRUE.equals(user.getIsBanned()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::fromEntity);
    }
}
