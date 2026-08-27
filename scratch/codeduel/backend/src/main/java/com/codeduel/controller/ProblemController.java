package com.codeduel.controller;

import com.codeduel.config.UserPrincipal;
import com.codeduel.dto.ApiResponse;
import com.codeduel.dto.ProblemDto;
import com.codeduel.model.Difficulty;
import com.codeduel.service.ProblemRecommendationService;
import com.codeduel.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@Tag(name = "Problems", description = "Endpoints for exploring 1,000-question DSA bank and practice recommendations")
public class ProblemController {

    private final ProblemService problemService;
    private final ProblemRecommendationService recommendationService;

    public ProblemController(ProblemService problemService, ProblemRecommendationService recommendationService) {
        this.problemService = problemService;
        this.recommendationService = recommendationService;
    }

    @GetMapping
    @Operation(summary = "Search and filter curated problems")
    public ResponseEntity<ApiResponse<Page<ProblemDto>>> getProblems(
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserPrincipal principal) {

        Long userId = principal != null ? principal.getId() : null;
        Page<ProblemDto> result = problemService.getFilteredProblems(
                difficulty,
                topic,
                search,
                PageRequest.of(page, size, Sort.by("id").ascending()),
                userId
        );
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get problem details with public test cases and starter codes")
    public ResponseEntity<ApiResponse<ProblemDto>> getProblemById(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal principal) {

        Long userId = principal != null ? principal.getId() : null;
        ProblemDto dto = problemService.getProblemDtoById(id, userId);
        return ResponseEntity.ok(ApiResponse.ok(dto));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get personalized smart recommendations for user")
    public ResponseEntity<ApiResponse<List<ProblemRecommendationService.RecommendationDto>>> getRecommendations(
            @AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.ok(ApiResponse.ok(List.of()));
        }
        List<ProblemRecommendationService.RecommendationDto> recs = recommendationService.getRecommendationsForUser(principal.getId());
        return ResponseEntity.ok(ApiResponse.ok(recs));
    }
}
