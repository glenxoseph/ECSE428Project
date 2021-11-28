package ECSE428Project.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ECSE428Project.dao.QuestionRepository;
import ECSE428Project.model.Question;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QuestionServiceTest {
	
	@Autowired
	private QuestionService questionService;
	
	@MockBean
	private QuestionRepository questionRepository;
	
	private static final String EXISTING_ID = "How many?";
	
	@BeforeEach
	public void setup() {
		Question question = new Question();
		question.setId(EXISTING_ID);
		when(questionRepository.findById(EXISTING_ID)).thenReturn(Optional.of(question));
		
		when(questionRepository.save(any(Question.class))).thenAnswer(obj -> {
			return obj.getArgument(0);
		});
	}
	
	@Test
	public void testCreateQuestion() {
		Question question = new Question();
		question.setId("New id");
		assertEquals(question, questionService.createQuestion(question));
		
	}
	
	@Test
	public void testRetrieveExistentQuestion() {
		Question question = new Question();
		question.setId(EXISTING_ID);
		assertEquals(question, questionService.getQuestion(EXISTING_ID));
	}
	
	@Test
	public void testRetrieveNonExistentQuestion() {
		assertNull(questionService.getQuestion("some id"));
	}
	
	@Test
	public void testFindAllQuestions() {
		List<Question> questions = new ArrayList<>();
		Question question = new Question();
		question.setId("q1");
		Question question2 = new Question();
		question.setId("q2");
		questions.add(question);
		questions.add(question2);
		
		when(questionRepository.findAll()).thenReturn(questions);
		List<Question> actual = questionService.getAllQuestions();
		assertArrayEquals(questions.toArray(), actual.toArray());
	}
}
