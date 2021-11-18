package ECSE428Project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import ECSE428Project.model.Question;
import ECSE428Project.model.Quiz;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuizControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objMapper;

	@BeforeEach
	public void setup() throws Exception{
		createQuizzes();
	}
	
	@Test
	public void testCreateQuiz() throws Exception {
		createQuizzes();
	}
	
	@Test
	public void testGetAllQuizzes() throws Exception{
		mockMvc.perform(get("/quiz/all").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(content().json("[{\"name\":\"The boring quiz\","
				+ "\"topic\":\"ALL\"},{\"name\":\"The boring quiz (part 2)\","
				+ "\"topic\":\"ALL\"}]"));
	}
	
	@Test
	public void testGetAllQuizzesNames() throws Exception{
		mockMvc.perform(get("/quiz/all/names").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(content().json("[\"The boring quiz\", \"The boring quiz (part 2)\"]"));
	}
	
	@Test
	public void testGetQuizByName() throws Exception{
		mockMvc.perform(get("/quiz/The%20boring%20quiz%20(part%202)").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(content().json("{\"name\":\"The boring quiz (part 2)\","
				+ "\"topic\":\"ALL\"}"));
	}
	
	@Test
	public void testGetQuizByNameNonExistent() throws Exception{
		mockMvc.perform(get("/quiz/The%20boring%20quiz%20(part%203)").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(status().reason("No quiz with name " + "The boring quiz (part 3)"  + " could be found."));
	}
	
	private void createQuizzes() throws Exception {
		List<Quiz> quizzes = new ArrayList<>();
		Quiz quiz = new Quiz();
		quiz.setName("The boring quiz");
		Question question = new Question();
		question.setAskedQuestion("How much is too much?");
		question.addPossibleAnswer("12");
		question.addPossibleAnswer("24");
		question.addPossibleAnswer("80");
		question.setAnswer("80");
		quiz.addQuestion(question);
		quizzes.add(quiz);

		Quiz quiz2 = new Quiz();
		quiz2.setName("The boring quiz (part 2)");
		Question question2 = new Question();
		question2.setAskedQuestion("How much is too little?");
		question2.addPossibleAnswer("12");
		question2.addPossibleAnswer("24");
		question2.addPossibleAnswer("80");
		question2.setAnswer("12");
		quiz2.addQuestion(question);
		quiz2.addQuestion(question2);
		quizzes.add(quiz2);

		mockMvc.perform(post("/quiz/create").content(objMapper.writeValueAsString(quizzes))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"name\":\"The boring quiz\","
						+ "\"topic\":\"ALL\"},{\"name\":\"The boring quiz (part 2)\","
						+ "\"topic\":\"ALL\"}]"));
	}

}
