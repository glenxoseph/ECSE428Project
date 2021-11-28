package ECSE428Project.controller;

import ECSE428Project.dto.AccountCreateDto;
import ECSE428Project.model.Account;
import ECSE428Project.model.TestUtilities;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Integration testing for Business logic/methods for question prompts with multiple choice
    @Test
    public void testMCQPrompts() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String quizId = "null";
        String quizName = "Post-Renaissance European Art";
        String quizTopic = "ART";
        String question = "Which artist pioneered the Italian Renaissance?";
        String choiceOne = "Picasso";
        String choiceTwo = "Da Vinci";
        String choiceThree = "Botticelli";
        String choiceFour = "Titan";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/quiz/create").content("[{\"id\":" + quizId
                                + ",\"name\":\"" + quizName
                                + "\",\"topic\":\"" + quizTopic
                                + "\",\"questions\":[{\"id\":null,\"askedQuestion\":\"" + question
                                + "\", \"possibleAnswers\":[\"" + choiceOne
                                + "\",\"" + choiceTwo + "\",\"" + choiceThree
                                + "\",\"" + choiceFour + "\"],\"answer\":\"" + choiceOne + "\"}]}]")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/quiz/all/names").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\"Post-Renaissance European Art\"]"));

    }

    @Test
    public void testCorrectAnswer() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String quizId = "null";
        String quizName = "Art";
        String quizTopic = "ART";
        String question = "Which artist pioneered the Italian Renaissance?";
        String choiceOne = "Picasso";
        String choiceTwo = "Da Vinci";
        String choiceThree = "Botticelli";
        String choiceFour = "Titan";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/quiz/create").content("[{\"id\":" + quizId
                                + ",\"name\":\"" + quizName
                                + "\",\"topic\":\"" + quizTopic
                                + "\",\"questions\":[{\"id\":null,\"askedQuestion\":\"" + question
                                + "\", \"possibleAnswers\":[\"" + choiceOne
                                + "\",\"" + choiceTwo + "\",\"" + choiceThree
                                + "\",\"" + choiceFour + "\"],\"answer\":\"" + choiceOne + "\"}]}]")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/quiz/" + quizName + "/questions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].question").value(question))
                .andExpect(jsonPath("$.[*].answer").value(choiceOne))
                .andExpect(jsonPath("$.[*].possibleAnswers[0]").value(choiceOne))
                .andExpect(jsonPath("$.[*].possibleAnswers[1]").value(choiceTwo))
                .andExpect(jsonPath("$.[*].possibleAnswers[2]").value(choiceThree))
                .andExpect(jsonPath("$.[*].possibleAnswers[3]").value(choiceFour))
                .andReturn();

    }

}
