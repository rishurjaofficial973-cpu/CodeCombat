package com.codeduel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @Column(length = 50, nullable = false)
    private String id; // e.g. "FIRST_WIN", "STREAK_5", "SPEED_DEMON"

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(length = 50)
    private String iconName; // e.g. "Trophy", "Zap", "Flame", "Target", "Award"

    @Column(length = 50)
    private String category; // "MATCHES", "STREAK", "EFFICIENCY", "RATING"

    @Column(nullable = false)
    private Integer points = 50;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Achievement() {}

    public Achievement(String id, String title, String description, String iconName, String category, Integer points) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconName = iconName;
        this.category = category;
        this.points = points != null ? points : 50;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
