package ECSE428Project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import ECSE428Project.dto.MatchDto;
import ECSE428Project.dto.PostGameStatDto;
import ECSE428Project.dto.QuestionDto;
import ECSE428Project.model.Account;
import ECSE428Project.model.Question;
import ECSE428Project.model.Quiz;
import ECSE428Project.model.TestUtilities;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameplayTest {
	// this class is meant to test the entire system including creating an account,
	// creating a game, playing the game
	// viewing the post game stats
	// viewing the updated account stats

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objMapper;

	private String matchId;

	private String email = "accountEmail1@a.ca";

	private void createAccount() throws Exception {
		Account account1 = TestUtilities.createAccount(1);
		mockMvc.perform(post("/createAccount")
				.content("{\n" + "\"name\": \"" + account1.getName() + "\",\n" + "\"email\": \"" + account1.getEmail()
						+ "\",\n" + "\"password\": \"" + account1.getPassword() + "\"\n" + "}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(content().json(
						"{\"name\":\"accountName1\",\"email\":\"accountEmail1@a.ca\",\"password\":\"password1\"}"));

	}

	private void createQuiz() throws Exception {
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

		Question question3 = new Question();
		question3.setAskedQuestion("How much is just enough?");
		question3.addPossibleAnswer("12");
		question3.addPossibleAnswer("24");
		question3.addPossibleAnswer("80");
		question3.setAnswer("24");

		quiz2.addQuestion(question);
		quiz2.addQuestion(question2);
		quiz2.addQuestion(question3);
		quizzes.add(quiz2);
		mockMvc.perform(post("/quiz/create").content(objMapper.writeValueAsString(quizzes))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"name\":\"The boring quiz\","
						+ "\"topic\":\"ALL\"},{\"name\":\"The boring quiz (part 2)\"," + "\"topic\":\"ALL\"}]"));
	}
	
	@Test
	public void testGameplay() throws Exception {
		createAccount();
		createQuiz();
		createGame();
		playGame();
	}

	private void createGame() throws Exception {
		Account account1 = TestUtilities.createAccount(1);
		List<String> emails = new ArrayList<>();
		emails.add(account1.getEmail());
		MvcResult result = mockMvc
				.perform(post("/match/create").content(objMapper.writeValueAsString(emails))
						.param("quizName", "The boring quiz (part 2)")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"accounts\":[{\"email\":\"accountEmail1@a.ca\",\"name\":\"accountName1\","
						+ "\"score\":0,\"level\":0,\"loggedIn\":false}]}"))
				.andReturn();
		matchId = (objMapper.readValue(result.getResponse().getContentAsString(), MatchDto.class)).getMatchId();
	}

	private void playGame() throws Exception {
		int nbRightAnswer = 0;
		QuestionDto questionDto = getNextQuestion(matchId, email);
		while (questionDto.getMessage() == null) {

			String guess = questionDto.getPossibleAnswers()[1];
			String answer = giveAnswerAndValidate(email, guess, matchId);
			if (answer.equals(guess)) {
				nbRightAnswer++;
			}
			//System.out.println("\n\n\n\n\n" + questionDto.getQuestion() + "\n\n\n\n\n");
			questionDto = getNextQuestion(matchId, email);
		}
		
		PostGameStatDto dto = getPostGameStat(matchId, email);
		assertEquals(nbRightAnswer, dto.getNumberOfCorrectAnswers());
		assertEquals(20 * nbRightAnswer, dto.getPointsGained());
	}

	private String giveAnswerAndValidate(String email, String guess, String matchId) throws Exception {
		HashMap<String, String> json = new HashMap<>();
		json.put("matchId", matchId);
		json.put("email", email);
		json.put("guess", guess);
		
		MvcResult result = mockMvc
				.perform(post("/match/quiz/verify")
						.content(objMapper.writeValueAsString(json)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		HashMap<String, String> map = null;
		map = objMapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
		return map.get("answer");
	}

	private QuestionDto getNextQuestion(String matchId, String email) throws Exception {

		MvcResult result = mockMvc
				.perform(get("/match/" + matchId + "/" + email +"/next"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String response = result.getResponse().getContentAsString();
		QuestionDto dto = objMapper.readValue(response, QuestionDto.class);
		return dto;
	}
	
	private PostGameStatDto getPostGameStat(String matchId, String email) throws Exception {
		MvcResult result = mockMvc
				.perform(get("/match/postGameStat/" + matchId + "/" + email))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String response = result.getResponse().getContentAsString();
		PostGameStatDto dto = objMapper.readValue(response, PostGameStatDto.class);
		return dto;
	}
}
