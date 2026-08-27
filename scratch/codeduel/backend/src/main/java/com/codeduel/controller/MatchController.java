package com.codeduel.controller;

import com.codeduel.config.UserPrincipal;
import com.codeduel.dto.ApiResponse;
import com.codeduel.dto.MatchResponseDto;
import com.codeduel.dto.MatchResultDto;
import com.codeduel.dto.MatchmakingRequest;
import com.codeduel.service.MatchService;
import com.codeduel.service.MatchmakingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Matches & Matchmaking", description = "Endpoints for finding 1v1 opponents, joining duels, and match history")
public class MatchController {

    private final MatchmakingService matchmakingService;
    private final MatchService matchService;

    public MatchController(MatchmakingService matchmakingService, MatchService matchService) {
        this.matchmakingService = matchmakingService;
        this.matchService = matchService;
    }

    @PostMapping("/find")
    @Operation(summary = "Enqueue player into Redis matchmaking queue with expanding rating window")
    public ResponseEntity<ApiResponse<Map<String, Object>>> findMatch(
            @RequestBody(required = false) MatchmakingRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {

        matchmakingService.enqueuePlayer(principal.getId(), request);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "SEARCHING");
        result.put("message", "Searching for opponent near your rating...");
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PostMapping("/cancel")
    @Operation(summary = "Cancel matchmaking queue search")
    public ResponseEntity<ApiResponse<Map<String, Object>>> cancelMatch(
            @AuthenticationPrincipal UserPrincipal principal) {

        matchmakingService.cancelSearch(principal.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("status", "CANCELLED");
        return ResponseEntity.ok(ApiResponse.ok("Matchmaking cancelled", result));
    }

    @GetMapping("/status")
    @Operation(summary = "Check matchmaking queue status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkQueueStatus(
            @AuthenticationPrincipal UserPrincipal principal) {

        boolean inQueue = matchmakingService.isUserInQueue(principal.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("inQueue", inQueue);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get match state and server remaining time")
    public ResponseEntity<ApiResponse<MatchResponseDto>> getMatch(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal principal) {

        MatchResponseDto matchDto = matchService.getMatchDto(id, principal.getId());
        return ResponseEntity.ok(ApiResponse.ok(matchDto));
    }

    @GetMapping("/{id}/result")
    @Operation(summary = "Get match result and deep performance analysis")
    public ResponseEntity<ApiResponse<MatchResultDto>> getMatchResult(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal principal) {

        MatchResultDto resultDto = matchService.getMatchResult(id, principal.getId());
        return ResponseEntity.ok(ApiResponse.ok(resultDto));
    }

    @GetMapping("/history")
    @Operation(summary = "Get historical matches for the current user")
    public ResponseEntity<ApiResponse<Page<MatchResponseDto>>> getMatchHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @AuthenticationPrincipal UserPrincipal principal) {

        Page<MatchResponseDto> history = matchService.getMatchHistory(principal.getId(), PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(history));
    }
}
