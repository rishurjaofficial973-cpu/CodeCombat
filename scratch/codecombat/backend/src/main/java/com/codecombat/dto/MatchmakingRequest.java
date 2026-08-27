package com.codecombat.dto;

import com.codecombat.model.Difficulty;
import com.codecombat.model.MatchMode;

public class MatchmakingRequest {
    private Difficulty preferredDifficulty; // null means ANY
    private String preferredTopic;         // null means ANY
    private MatchMode mode = MatchMode.SCORE;

    public MatchmakingRequest() {}

    public MatchmakingRequest(Difficulty preferredDifficulty, String preferredTopic, MatchMode mode) {
        this.preferredDifficulty = preferredDifficulty;
        this.preferredTopic = preferredTopic;
        this.mode = mode != null ? mode : MatchMode.SCORE;
    }

    public Difficulty getPreferredDifficulty() { return preferredDifficulty; }
    public void setPreferredDifficulty(Difficulty preferredDifficulty) { this.preferredDifficulty = preferredDifficulty; }

    public String getPreferredTopic() { return preferredTopic; }
    public void setPreferredTopic(String preferredTopic) { this.preferredTopic = preferredTopic; }

    public MatchMode getMode() { return mode; }
    public void setMode(MatchMode mode) { this.mode = mode; }
}
