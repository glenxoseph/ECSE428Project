package ECSE428Project.dto;

import ECSE428Project.model.Question;

public class QuestionDto {

	String questionId;
	String question;
	String[] possibleAnswers;
	String message;

	public String getMessage() {
		return message;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static QuestionDto toDto(Question question) {
		QuestionDto dto = new QuestionDto();
		dto.question = question.getAskedQuestion();
		dto.questionId = question.getId();
		dto.possibleAnswers = new String[question.getPossibleAnswers().size()];
		for (int i = 0; i < question.getPossibleAnswers().size(); i++) {
			dto.possibleAnswers[i] = question.getPossibleAnswers().get(i);
		}
		return dto;
	}
	
	public static QuestionDto empty() {
		QuestionDto dto = new QuestionDto();
		dto.message = "empty";
		return dto;
	}
	
	public QuestionDto() {
		message = "-";
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getPossibleAnswers() {
		return possibleAnswers;
	}

	public void setPossibleAnswers(String[] possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
}
