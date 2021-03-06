package ECSE428Project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import ECSE428Project.model.Account;
import ECSE428Project.model.Question;
import ECSE428Project.model.Quiz;
import ECSE428Project.model.TestUtilities;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MatchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objMapper;
	
	@BeforeEach
	public void prepareQuizzes() throws Exception {
		createQuizzes();
	}


	@Test
	public void testCreateMatch() throws Exception {
		createDefaultSetup();
		int i = 0;
		// note that the json output is more substantial, but only checking the accounts is enough (checking the id is
		// useless since it is autogenerated)
		Account account1 = TestUtilities.createAccount(i++);
		Account account2 = TestUtilities.createAccount(i++);
		Account account3 = TestUtilities.createAccount(i++);
		List<String> emails = new ArrayList<>();
		emails.add(account1.getEmail());
		emails.add(account2.getEmail());
		emails.add(account3.getEmail());
		mockMvc.perform(post("/match/create").content(objMapper.writeValueAsString(emails))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"accounts\":[{\"email\":\"accountEmail1@a.ca\",\"name\":\"accountName1\","
						+ "\"score\":0,\"level\":0,\"loggedIn\":false},"
						+ "{\"email\":\"accountEmail0@a.ca\",\"name\":\"accountName0\",\"score\""
						+ ":0,\"level\":0,\"loggedIn\":false},{\"email\":\"accountEmail2@a.ca\",\"name\""
						+ ":\"accountName2\",\"score\":0,\"level\":0,\"loggedIn\":false}]}"));}

	@Test
	public void testCreateMatchWrongEmail() throws Exception {
		createDefaultSetup();
		int i = 0;
		Account account1 = TestUtilities.createAccount(i++);
		Account account2 = TestUtilities.createAccount(i++);
		Account account3 = TestUtilities.createAccount(i + 2);
		List<String> emails = new ArrayList<>();
		emails.add(account1.getEmail());
		emails.add(account2.getEmail());
		emails.add(account3.getEmail());

		mockMvc.perform(post("/match/create").content(objMapper.writeValueAsString(emails))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(status().reason("No account with email " + account3.getEmail()));
	}

	@Test
	public void testGetAllMatch() throws Exception {
		createDefaultSetup();
		mockMvc.perform(get("/match/history")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"winnerId\":null,"
						+ "\"winnerName\":null,\"gameMode\":\"SOLO\",\"options\":null,\"accounts\":[{\"email\":"
						+ "\"accountEmail0@a.ca\",\"name\":\"accountName0\",\""
						+ "score\":0,\"level\":0,\"loggedIn\":false},{\"email\":"
						+ "\"accountEmail1@a.ca\",\"name\":\"accountName1\","
						+ "\"score\":0,\"level\":0,\"loggedIn\":false},{\"email\":"
						+ "\"accountEmail2@a.ca\",\"name\":\"accountName2\","
						+ "\"score\":0,\"level\":0,\"loggedIn\":false}]}]"));
	}
	
	@Test
	public void testGetAllMatchWhenNoMatchPresent() throws Exception {
		mockMvc.perform(get("/match/history")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[]"));
	}

	@Test
	public void testGetMatchByEmail() throws Exception {
		createDefaultSetup();
		int i = 0;
		Account account1 = TestUtilities.createAccount(i);
		mockMvc.perform(get("/match/history/" + account1.getEmail())).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"winnerId\":null,"
						+ "\"winnerName\":null,\"gameMode\":\"SOLO\",\"options\":null,\"accounts\":[{\"email\":"
						+ "\"accountEmail0@a.ca\",\"name\":\"accountName0\",\""
						+ "score\":0,\"level\":0,\"loggedIn\":false},{\"email\":"
						+ "\"accountEmail1@a.ca\",\"name\":\"accountName1\","
						+ "\"score\":0,\"level\":0,\"loggedIn\":false},{\"email\":"
						+ "\"accountEmail2@a.ca\",\"name\":\"accountName2\","
						+ "\"score\":0,\"level\":0,\"loggedIn\":false}]}]"));
	}
	
	@Test
	public void testGetMatchByEmailNoMatch() throws Exception {
		int i = 0;
		Account account1 = TestUtilities.createAccount(i);
		mockMvc.perform(
				post("/createAccount").content("{\n" + "\"name\": \"" + account1.getName() + "\",\n" + "\"email\": \""
						+ account1.getEmail() + "\",\n" + "\"password\": \"" + account1.getPassword() + "\"\n" + "}")
						.contentType(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(get("/match/history/" + account1.getEmail())).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[]"));
	}

	@Test
	public void testGetMatchByEmailWrongEmail() throws Exception {
		createDefaultSetup();
		int i = 4;
		Account account1 = TestUtilities.createAccount(i);
		mockMvc.perform(get("/match/history/" + account1.getEmail())).andExpect(status().isBadRequest())
				.andExpect(status().reason("No account with the corresponding email can be found."));
	}
	
	private void createDefaultSetup() throws Exception {
		int i = 0;
		Account account1 = TestUtilities.createAccount(i++);
		Account account2 = TestUtilities.createAccount(i++);
		Account account3 = TestUtilities.createAccount(i++);
		List<String> emails = new ArrayList<>();
		emails.add(account1.getEmail());
		emails.add(account2.getEmail());
		emails.add(account3.getEmail());

		mockMvc.perform(
				post("/createAccount").content("{\n" + "\"name\": \"" + account1.getName() + "\",\n" + "\"email\": \""
						+ account1.getEmail() + "\",\n" + "\"password\": \"" + account1.getPassword() + "\"\n" + "}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(result -> mockMvc
						.perform(
								post("/createAccount")
										.content("{\n" + "\"name\": \"" + account2.getName() + "\",\n" + "\"email\": \""
												+ account2.getEmail() + "\",\n" + "\"password\": \""
												+ account2.getPassword() + "\"\n" + "}")
										.contentType(MediaType.APPLICATION_JSON))
						.andDo(result2 -> mockMvc
								.perform(post("/createAccount")
										.content("{\n" + "\"name\": \"" + account3.getName() + "\",\n" + "\"email\": \""
												+ account3.getEmail() + "\",\n" + "\"password\": \""
												+ account3.getPassword() + "\"\n" + "}")
										.contentType(MediaType.APPLICATION_JSON))
								.andDo(result3 -> mockMvc.perform(
										post("/match/create").content(objMapper.writeValueAsString(emails))
										.contentType(MediaType.APPLICATION_JSON)))));
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
