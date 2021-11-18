package ECSE428Project.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ECSE428Project.dao.QuizRepository;
import ECSE428Project.model.Quiz;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Transactional
	public Quiz createQuiz(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	@Transactional
	public List<Quiz> createQuizzes(List<Quiz> quizzes) {
		List<Quiz> createdQuizzes = new ArrayList<>();
		for (Quiz quiz : quizzes) {
			Quiz created = createQuiz(quiz);
			if (created != null) {
				createdQuizzes.add(created);
			}
		}
		return createdQuizzes;
	}

	@Transactional
	public List<Quiz> getAllQuizzes() {
		List<Quiz> quizzes = new ArrayList<>();
		quizRepository.findAll().iterator().forEachRemaining(quiz -> quizzes.add(quiz));
		return quizzes;
	}

	@Transactional
	public List<String> getAllQuizzesNames() {
		List<String> quizzesNames = new ArrayList<>();
		quizRepository.findAll().iterator().forEachRemaining(quiz -> quizzesNames.add(quiz.getName()));
		return quizzesNames;
	}

	@Transactional
	public Quiz getQuizById(String quizId) {
		return quizRepository.findById(quizId).orElse(null);
	}

	@Transactional
	public Quiz getQuizByName(String quizName) {
		return quizRepository.findByName(quizName);
	}

}
