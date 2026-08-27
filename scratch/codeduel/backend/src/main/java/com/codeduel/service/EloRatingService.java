package com.codeduel.service;

import com.codeduel.model.Match;
import com.codeduel.model.RatingHistory;
import com.codeduel.model.User;
import com.codeduel.repository.RatingHistoryRepository;
import com.codeduel.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EloRatingService {

    private static final Logger log = LoggerFactory.getLogger(EloRatingService.class);

    private final UserRepository userRepository;
    private final RatingHistoryRepository ratingHistoryRepository;
    private final LeaderboardService leaderboardService;

    public EloRatingService(UserRepository userRepository,
                            RatingHistoryRepository ratingHistoryRepository,
                            LeaderboardService leaderboardService) {
        this.userRepository = userRepository;
        this.ratingHistoryRepository = ratingHistoryRepository;
        this.leaderboardService = leaderboardService;
    }

    public static class RatingDelta {
        public final int player1Change;
        public final int player2Change;
        public final int player1NewRating;
        public final int player2NewRating;

        public RatingDelta(int p1Change, int p2Change, int p1New, int p2New) {
            this.player1Change = p1Change;
            this.player2Change = p2Change;
            this.player1NewRating = p1New;
            this.player2NewRating = p2New;
        }
    }

    public int getKFactor(User user) {
        int matches = user.getMatchesPlayed() != null ? user.getMatchesPlayed() : 0;
        int rating = user.getRating() != null ? user.getRating() : 1200;

        if (matches < 10) return 40;
        if (rating < 1600) return 32;
        if (rating < 2000) return 24;
        return 16;
    }

    public double getExpectedScore(int playerRating, int opponentRating) {
        return 1.0 / (1.0 + Math.pow(10.0, (double) (opponentRating - playerRating) / 400.0));
    }

    public RatingDelta calculateElo(User p1, User p2, double scoreP1) {
        int r1 = p1.getRating();
        int r2 = p2.getRating();

        double expected1 = getExpectedScore(r1, r2);
        double expected2 = getExpectedScore(r2, r1);

        double scoreP2 = 1.0 - scoreP1;

        int k1 = getKFactor(p1);
        int k2 = getKFactor(p2);

        int delta1 = (int) Math.round(k1 * (scoreP1 - expected1));
        int delta2 = (int) Math.round(k2 * (scoreP2 - expected2));

        // Ensure at least +1 / -1 on decisive win
        if (scoreP1 == 1.0 && delta1 <= 0) delta1 = 1;
        if (scoreP2 == 1.0 && delta2 <= 0) delta2 = 1;
        if (scoreP1 == 0.0 && delta1 >= 0) delta1 = -1;
        if (scoreP2 == 0.0 && delta2 >= 0) delta2 = -1;

        int newR1 = Math.max(100, r1 + delta1);
        int newR2 = Math.max(100, r2 + delta2);

        return new RatingDelta(delta1, delta2, newR1, newR2);
    }

    @Transactional
    public RatingDelta applyMatchRating(User p1, User p2, Match match, Long winnerId, boolean isDraw) {
        double scoreP1 = isDraw ? 0.5 : (p1.getId().equals(winnerId) ? 1.0 : 0.0);
        RatingDelta delta = calculateElo(p1, p2, scoreP1);

        int oldR1 = p1.getRating();
        int oldR2 = p2.getRating();

        // Update User 1
        p1.setRating(delta.player1NewRating);
        p1.setMatchesPlayed(p1.getMatchesPlayed() + 1);
        if (isDraw) {
            p1.setDraws(p1.getDraws() + 1);
            p1.setWinStreak(0);
        } else if (p1.getId().equals(winnerId)) {
            p1.setWins(p1.getWins() + 1);
            p1.setWinStreak(p1.getWinStreak() + 1);
            p1.setBestWinStreak(Math.max(p1.getBestWinStreak(), p1.getWinStreak()));
        } else {
            p1.setLosses(p1.getLosses() + 1);
            p1.setWinStreak(0);
        }

        // Update User 2
        p2.setRating(delta.player2NewRating);
        p2.setMatchesPlayed(p2.getMatchesPlayed() + 1);
        if (isDraw) {
            p2.setDraws(p2.getDraws() + 1);
            p2.setWinStreak(0);
        } else if (p2.getId().equals(winnerId)) {
            p2.setWins(p2.getWins() + 1);
            p2.setWinStreak(p2.getWinStreak() + 1);
            p2.setBestWinStreak(Math.max(p2.getBestWinStreak(), p2.getWinStreak()));
        } else {
            p2.setLosses(p2.getLosses() + 1);
            p2.setWinStreak(0);
        }

        userRepository.save(p1);
        userRepository.save(p2);

        // Record history
        ratingHistoryRepository.save(new RatingHistory(p1, match, oldR1, delta.player1NewRating, delta.player1Change, oldR2, p2.getUsername()));
        ratingHistoryRepository.save(new RatingHistory(p2, match, oldR2, delta.player2NewRating, delta.player2Change, oldR1, p1.getUsername()));

        // Sync Redis leaderboard
        leaderboardService.updateUserRating(p1.getId(), delta.player1NewRating);
        leaderboardService.updateUserRating(p2.getId(), delta.player2NewRating);

        log.info("Elo updated for match {}: {} ({} -> {} [{}]), {} ({} -> {} [{}])",
                match.getId(), p1.getUsername(), oldR1, delta.player1NewRating, delta.player1Change,
                p2.getUsername(), oldR2, delta.player2NewRating, delta.player2Change);

        return delta;
    }
}
