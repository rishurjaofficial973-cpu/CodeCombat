package com.codeduel.controller;

import com.codeduel.config.UserPrincipal;
import com.codeduel.dto.ApiResponse;
import com.codeduel.dto.UserProfileDto;
import com.codeduel.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Performance metrics, rating progression, and topic mastery")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get analytics profile for current user")
    public ResponseEntity<ApiResponse<UserProfileDto>> getMyAnalytics(@AuthenticationPrincipal UserPrincipal principal) {
        UserProfileDto profile = analyticsService.getUserProfile(principal.getId());
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get analytics profile for specified user")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserAnalytics(@PathVariable Long id) {
        UserProfileDto profile = analyticsService.getUserProfile(id);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }
}
