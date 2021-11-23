package ECSE428Project.dto;

import ECSE428Project.model.Quiz;

public class QuizDto {
	
	private String name;
	private String topic;
	private int nbQuestion;
	
	public static QuizDto toDto(Quiz quiz) {
		QuizDto dto = new QuizDto();
		dto.setName(quiz.getName());
		dto.setTopic(quiz.getTopic() == null ? null : quiz.getTopic().toString());
		dto.setNbQuestion(quiz.getQuestions().size());
		
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getNbQuestion() {
		return nbQuestion;
	}

	public void setNbQuestion(int nbQuestion) {
		this.nbQuestion = nbQuestion;
	}

}
