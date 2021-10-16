package ECSE428Project.controller;

import ECSE428Project.dto.AccountCreateDto;
import ECSE428Project.model.Account;
import ECSE428Project.model.TestUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"accountName1\",\"email\":\"accountEmail1@a.ca\",\"password\":\"password1\"}"));
    }


    @Test
    public void testCreateAccountExcludeAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        mockMvc.perform(post("/createAccount").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Invalid account"));
    }


    @Test
    public void testCreateAccountInvalidEmail() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail("Invalid email address");
        accountCreateDto.setPassword(account.getPassword());

        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This is not a valid email address"));
    }
}