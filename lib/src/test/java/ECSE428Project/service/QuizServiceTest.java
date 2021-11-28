package ECSE428Project.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ECSE428Project.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ECSE428Project.dao.QuizRepository;
import ECSE428Project.model.Quiz;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QuizServiceTest {
	
	@Autowired
	private QuizService quizService;
	
	@MockBean
	private QuizRepository quizRepository;
	
	private static final String EXISTING_ID = "How many?";
	private static final String EXISTING_NAME = "My name";
	
	@BeforeEach
	public void setup() {
		Quiz quiz = new Quiz();
		quiz.setId(EXISTING_ID);
		when(quizRepository.findById(EXISTING_ID)).thenReturn(Optional.of(quiz));
		when(quizRepository.findByName(EXISTING_NAME)).thenReturn(quiz);
		when(quizRepository.save(any(Quiz.class))).thenAnswer(obj -> {
			return obj.getArgument(0);
		});
	}
	
	@Test
	public void testCreateQuiz() {
		Quiz quiz = new Quiz();
		quiz.setId("New id");
		assertEquals(quiz, quizService.createQuiz(quiz));
		
	}
	
	@Test
	public void testRetrieveExistentQuiz() {
		Quiz quiz = new Quiz();
		quiz.setId(EXISTING_ID);
		assertEquals(quiz, quizService.getQuizById(EXISTING_ID));
		assertEquals(quiz, quizService.getQuizByName(EXISTING_NAME));
	}
	
	@Test
	public void testRetrieveNonExistentQuiz() {
		assertNull(quizService.getQuizById("some id"));
		assertDoesNotThrow(() -> quizService.getQuizById("some id"));
	}
	
	@Test
	public void testFindAllQuizs() {
		List<Quiz> quizs = new ArrayList<>();
		Quiz quiz = new Quiz();
		quiz.setId("q1");
		Quiz quiz2 = new Quiz();
		quiz.setId("q2");
		quizs.add(quiz);
		quizs.add(quiz2);
		
		when(quizRepository.findAll()).thenReturn(quizs);
		List<Quiz> actual = quizService.getAllQuizzes();
		assertArrayEquals(quizs.toArray(), actual.toArray());
	}

	@Test
	public void testGetQuizQuestions() {
		// Initialize a quiz and a question
		Quiz quiz = new Quiz();
		quiz.setName("quizName");

		List<String> answers = new ArrayList();
		answers.add("1");
		answers.add("2");
		answers.add("4");

		Question question = new Question("What is 2+2?", answers, "4");
		quiz.addQuestion(question);

		// Mock the repository
		when(quizRepository.findByName(quiz.getName())).thenReturn(quiz);

		// Call the get quiz questions method
		List<Question> questions = quizService.getQuizQuestions(quiz.getName());

		// Verify that the output is as expected
		assertEquals(questions.size(), 1);
		assertEquals(questions.get(0).getAskedQuestion(), question.getAskedQuestion());
	}
}