package ECSE428Project.dto;

import ECSE428Project.model.Leaderboard;

public class LeaderboardDto {

    private String quizName;
    private String quizScore;
    private String accountEmail;
    private String id;

    public static LeaderboardDto toDto(Leaderboard leaderboard) {
        LeaderboardDto dto = new LeaderboardDto();
        dto.quizName = leaderboard.getQuizName();
        dto.quizScore = leaderboard.getQuizScore();
        dto.accountEmail = leaderboard.getAccountEmail();
        dto.id = leaderboard.getId();
        return dto;
    }

    public void setQuizName(String quizName) { this.quizName = quizName; }

    public String getQuizName() { return quizName; }

    public void setQuizScore(String quizScore) { this.quizScore = quizScore; }

    public String getQuizScore() { return quizScore; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAccountEmail() { return accountEmail; }

    public void setAccountEmail(String accountEmail) { this.accountEmail = accountEmail; }
}
