package com.codecombat.dto;

public class AdminStatsDto {
    private Long totalUsers;
    private Long totalMatches;
    private Long totalProblems;
    private Long totalSubmissions;
    private Long activeMatches;
    private Long easyProblems;
    private Long mediumProblems;
    private Long hardProblems;

    public AdminStatsDto() {}

    public Long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }

    public Long getTotalMatches() { return totalMatches; }
    public void setTotalMatches(Long totalMatches) { this.totalMatches = totalMatches; }

    public Long getTotalProblems() { return totalProblems; }
    public void setTotalProblems(Long totalProblems) { this.totalProblems = totalProblems; }

    public Long getTotalSubmissions() { return totalSubmissions; }
    public void setTotalSubmissions(Long totalSubmissions) { this.totalSubmissions = totalSubmissions; }

    public Long getActiveMatches() { return activeMatches; }
    public void setActiveMatches(Long activeMatches) { this.activeMatches = activeMatches; }

    public Long getEasyProblems() { return easyProblems; }
    public void setEasyProblems(Long easyProblems) { this.easyProblems = easyProblems; }

    public Long getMediumProblems() { return mediumProblems; }
    public void setMediumProblems(Long mediumProblems) { this.mediumProblems = mediumProblems; }

    public Long getHardProblems() { return hardProblems; }
    public void setHardProblems(Long hardProblems) { this.hardProblems = hardProblems; }
}
