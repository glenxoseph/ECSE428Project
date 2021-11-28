package ECSE428Project.dto;

public class PostGameStatDto {

	int pointsGained;
	int levelGained;
	int numberOfCorrectAnswers;
	public int getPointsGained() {
		return pointsGained;
	}
	public void setPointsGained(int pointsGained) {
		this.pointsGained = pointsGained;
	}
	public int getLevelGained() {
		return levelGained;
	}
	public void setLevelGained(int levelGained) {
		this.levelGained = levelGained;
	}
	public int getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}
	public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
		this.numberOfCorrectAnswers = numberOfCorrectAnswers;
	}
}
