package com.codeduel.controller;

import com.codeduel.config.UserPrincipal;
import com.codeduel.dto.ApiResponse;
import com.codeduel.dto.UserDto;
import com.codeduel.dto.UserProfileDto;
import com.codeduel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User profiles, ranks, and statistical overviews")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getMyInfo(@AuthenticationPrincipal UserPrincipal principal) {
        UserDto dto = userService.getUserDtoById(principal.getId());
        return ResponseEntity.ok(ApiResponse.ok(dto));
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> getMyProfile(@AuthenticationPrincipal UserPrincipal principal) {
        UserProfileDto profile = userService.getUserProfile(principal.getId());
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto dto = userService.getUserDtoById(id);
        return ResponseEntity.ok(ApiResponse.ok(dto));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserProfileById(@PathVariable Long id) {
        UserProfileDto profile = userService.getUserProfile(id);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }
}
