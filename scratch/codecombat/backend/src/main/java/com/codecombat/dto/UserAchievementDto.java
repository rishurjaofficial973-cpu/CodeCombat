package com.codecombat.dto;

import com.codecombat.model.UserAchievement;

import java.time.LocalDateTime;

public class UserAchievementDto {
    private String id;
    private String title;
    private String description;
    private String iconName;
    private String category;
    private Integer points;
    private LocalDateTime unlockedAt;

    public UserAchievementDto() {}

    public static UserAchievementDto fromEntity(UserAchievement ua) {
        if (ua == null) return null;
        UserAchievementDto dto = new UserAchievementDto();
        dto.setId(ua.getAchievement().getId());
        dto.setTitle(ua.getAchievement().getTitle());
        dto.setDescription(ua.getAchievement().getDescription());
        dto.setIconName(ua.getAchievement().getIconName());
        dto.setCategory(ua.getAchievement().getCategory());
        dto.setPoints(ua.getAchievement().getPoints());
        dto.setUnlockedAt(ua.getUnlockedAt());
        return dto;
    }

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

    public LocalDateTime getUnlockedAt() { return unlockedAt; }
    public void setUnlockedAt(LocalDateTime unlockedAt) { this.unlockedAt = unlockedAt; }
}
