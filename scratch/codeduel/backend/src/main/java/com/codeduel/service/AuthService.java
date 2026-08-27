package com.codeduel.service;

import com.codeduel.config.JwtTokenProvider;
import com.codeduel.dto.AuthRequest;
import com.codeduel.dto.AuthResponse;
import com.codeduel.dto.RegisterRequest;
import com.codeduel.exception.BadRequestException;
import com.codeduel.model.Role;
import com.codeduel.model.User;
import com.codeduel.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final LeaderboardService leaderboardService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       LeaderboardService leaderboardService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.leaderboardService = leaderboardService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BadRequestException("Username '" + req.getUsername() + "' is already taken.");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email '" + req.getEmail() + "' is already registered.");
        }

        User user = new User(req.getUsername(), req.getEmail(), passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setRating(1200);
        user = userRepository.save(user);

        leaderboardService.updateUserRating(user.getId(), user.getRating());

        String token = tokenProvider.generateToken(user.getUsername(), user.getId(), user.getRole().name());
        Integer rank = leaderboardService.getUserRank(user.getId());

        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getRating(), rank);
    }

    public AuthResponse login(AuthRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = tokenProvider.generateToken(user.getUsername(), user.getId(), user.getRole().name());
        Integer rank = leaderboardService.getUserRank(user.getId());

        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getRating(), rank);
    }
}
