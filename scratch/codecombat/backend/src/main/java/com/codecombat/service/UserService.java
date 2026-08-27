package com.codecombat.service;

import com.codecombat.dto.UserDto;
import com.codecombat.dto.UserProfileDto;
import com.codecombat.exception.ResourceNotFoundException;
import com.codecombat.model.User;
import com.codecombat.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AnalyticsService analyticsService;
    private final LeaderboardService leaderboardService;

    public UserService(UserRepository userRepository, AnalyticsService analyticsService, LeaderboardService leaderboardService) {
        this.userRepository = userRepository;
        this.analyticsService = analyticsService;
        this.leaderboardService = leaderboardService;
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        UserDto dto = UserDto.fromEntity(user);
        dto.setGlobalRank(leaderboardService.getUserRank(user.getId()));
        return dto;
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        UserDto dto = UserDto.fromEntity(user);
        dto.setGlobalRank(leaderboardService.getUserRank(user.getId()));
        return dto;
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long userId) {
        return analyticsService.getUserProfile(userId);
    }
}
