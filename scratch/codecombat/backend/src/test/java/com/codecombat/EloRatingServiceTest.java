package com.codecombat;

import com.codecombat.model.User;
import com.codecombat.service.EloRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EloRatingServiceTest {

    private EloRatingService eloService;

    @BeforeEach
    void setUp() {
        eloService = new EloRatingService(null, null, null);
    }

    @Test
    void testExpectedScoreSymmetry() {
        double exp1 = eloService.getExpectedScore(1500, 1500);
        double exp2 = eloService.getExpectedScore(1500, 1500);

        assertEquals(0.5, exp1, 0.001);
        assertEquals(0.5, exp2, 0.001);
    }

    @Test
    void testHigherRatedHasHigherExpectedScore() {
        double expHigh = eloService.getExpectedScore(1800, 1400);
        double expLow = eloService.getExpectedScore(1400, 1800);

        assertTrue(expHigh > 0.90, "1800 vs 1400 should have high expected score");
        assertTrue(expLow < 0.10, "1400 vs 1800 should have low expected score");
        assertEquals(1.0, expHigh + expLow, 0.001);
    }

    @Test
    void testEqualRatingWinDelta() {
        User p1 = new User("p1", "p1@test.com", "hash");
        p1.setRating(1500);
        p1.setMatchesPlayed(20);

        User p2 = new User("p2", "p2@test.com", "hash");
        p2.setRating(1500);
        p2.setMatchesPlayed(20);

        EloRatingService.RatingDelta delta = eloService.calculateElo(p1, p2, 1.0); // p1 wins

        assertEquals(16, delta.player1Change, "Equal rating win with K=32 should give +16");
        assertEquals(-16, delta.player2Change, "Equal rating loss with K=32 should give -16");
        assertEquals(1516, delta.player1NewRating);
        assertEquals(1484, delta.player2NewRating);
    }

    @Test
    void testUnderdogWinGivesHigherDelta() {
        User underdog = new User("underdog", "u@test.com", "hash");
        underdog.setRating(1300);
        underdog.setMatchesPlayed(25);

        User favorite = new User("favorite", "f@test.com", "hash");
        favorite.setRating(1700);
        favorite.setMatchesPlayed(25);

        EloRatingService.RatingDelta delta = eloService.calculateElo(underdog, favorite, 1.0);

        assertTrue(delta.player1Change >= 25, "Underdog beating favorite should give large positive rating gain");
    }
}
