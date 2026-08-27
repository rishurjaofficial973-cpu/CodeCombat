package com.codecombat.controller;

import com.codecombat.dto.ApiResponse;
import com.codecombat.dto.LeaderboardEntryDto;
import com.codecombat.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@Tag(name = "Leaderboard", description = "Global top ratings and rank querying")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    @Operation(summary = "Get global leaderboard rankings")
    public ResponseEntity<ApiResponse<List<LeaderboardEntryDto>>> getLeaderboard(
            @RequestParam(defaultValue = "50") int limit) {
        List<LeaderboardEntryDto> leaderboard = leaderboardService.getTopLeaderboard(limit);
        return ResponseEntity.ok(ApiResponse.ok(leaderboard));
    }
}
