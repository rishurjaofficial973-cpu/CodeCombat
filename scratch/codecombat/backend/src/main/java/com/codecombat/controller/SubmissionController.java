package com.codecombat.controller;

import com.codecombat.config.UserPrincipal;
import com.codecombat.dto.ApiResponse;
import com.codecombat.dto.SubmissionRequestDto;
import com.codecombat.dto.SubmissionResponseDto;
import com.codecombat.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
@Tag(name = "Submissions", description = "Endpoints for running code against test cases and judging matches")
public class SubmissionController {

    private final MatchService matchService;

    public SubmissionController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    @Operation(summary = "Submit code solution for judging")
    public ResponseEntity<ApiResponse<SubmissionResponseDto>> submitCode(
            @Valid @RequestBody SubmissionRequestDto request,
            @AuthenticationPrincipal UserPrincipal principal) {

        SubmissionResponseDto response = matchService.submitCode(request, principal.getId());
        return ResponseEntity.ok(ApiResponse.ok("Judging completed", response));
    }
}
