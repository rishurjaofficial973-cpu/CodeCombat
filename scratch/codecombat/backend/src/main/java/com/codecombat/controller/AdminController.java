package com.codecombat.controller;

import com.codecombat.dto.AdminStatsDto;
import com.codecombat.dto.ApiResponse;
import com.codecombat.dto.ProblemDto;
import com.codecombat.dto.UserDto;
import com.codecombat.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Admin", description = "Administration operations for problem bank, stats, and moderation")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Get overall platform administration metrics")
    public ResponseEntity<ApiResponse<AdminStatsDto>> getStats() {
        AdminStatsDto stats = adminService.getAdminStats();
        return ResponseEntity.ok(ApiResponse.ok(stats));
    }

    @PostMapping("/problems")
    @Operation(summary = "Create a new problem in the question bank")
    public ResponseEntity<ApiResponse<ProblemDto>> createProblem(@Valid @RequestBody ProblemDto dto) {
        ProblemDto created = adminService.createProblem(dto);
        return ResponseEntity.ok(ApiResponse.ok("Problem created", created));
    }

    @PutMapping("/problems/{id}")
    @Operation(summary = "Update an existing problem")
    public ResponseEntity<ApiResponse<ProblemDto>> updateProblem(@PathVariable String id, @RequestBody ProblemDto dto) {
        ProblemDto updated = adminService.updateProblem(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Problem updated", updated));
    }

    @DeleteMapping("/problems/{id}")
    @Operation(summary = "Deactivate/delete a problem")
    public ResponseEntity<ApiResponse<Void>> deleteProblem(@PathVariable String id) {
        adminService.deleteProblem(id);
        return ResponseEntity.ok(ApiResponse.ok("Problem deleted", null));
    }

    @GetMapping("/users")
    @Operation(summary = "Get paginated user list for moderation")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserDto> users = adminService.getAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(users));
    }

    @PostMapping("/users/{id}/ban")
    @Operation(summary = "Toggle user ban/unban status")
    public ResponseEntity<ApiResponse<Void>> toggleBan(@PathVariable Long id) {
        adminService.toggleUserBan(id);
        return ResponseEntity.ok(ApiResponse.ok("User status updated", null));
    }
}
