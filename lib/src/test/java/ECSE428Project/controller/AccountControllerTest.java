package ECSE428Project.controller;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dto.AccountCreateDto;
import ECSE428Project.model.Account;
import ECSE428Project.model.TestUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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


    @Test
    public void testGetAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        // Create and save an account to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Get the account and verify that the output is as expected
        mockMvc.perform(get("/account/" + accountCreateDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"email\":\"accountEmail1@a.ca\",\"name\":\"accountName1\",\"password\":\"password1\",\"score\":0,\"level\":0,\"verified\":false,\"loggedIn\":false,\"admin\":false}"));
    }


    @Test
    public void testGetNullAccount() throws Exception {
        String email = "wrongEmail@mail.com";

        // Verify that an exception is thrown when the methods gets a non-existing email
        mockMvc.perform(get("/account/" + email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
  @Test
  public void testChangePassword() throws Exception {
    Account account = TestUtilities.createAccount(1);
    String newPassword = "brandNew";
    mockMvc
        .perform(post("/createAccount")
            .content("{\n" + "\"name\": \"" + account.getName() + "\",\n" + "\"email\": \"" + account.getEmail()
                + "\",\n" + "\"password\": \"" + account.getPassword() + "\"\n" + "}")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(result ->

        mockMvc
            .perform(post("/users/{email}/changePassword", account.getEmail()).content("{\n" + "\"oldPassword\": \""
                + account.getPassword() + "\",\n" + "\"newPassword\": \"" + newPassword + "\"\n" + "}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(content()
                .json("{\"name\":\"accountName1\",\"email\":\"accountEmail1@a.ca\",\"password\":\"brandNew\"}")));
  }

  @Test
  public void testChangePasswordNoAccount() throws Exception {
    Account account = TestUtilities.createAccount(1);
    String newPassword = "brandNew";

    mockMvc
        .perform(post("/users/{email}/changePassword", account.getEmail()).content("{\n" + "\"oldPassword\": \""
            + account.getPassword() + "\",\n" + "\"newPassword\": \"" + newPassword + "\"\n" + "}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(status().reason("No account associated to the email accountEmail1@a.ca exists"));
  }
  
  @Test
  public void testChangePasswordWrongPassword() throws Exception {
    Account account = TestUtilities.createAccount(1);
    String newPassword = "brandNew";
    mockMvc
        .perform(post("/createAccount")
            .content("{\n" + "\"name\": \"" + account.getName() + "\",\n" + "\"email\": \"" + account.getEmail()
                + "\",\n" + "\"password\": \"" + account.getPassword() + "\"\n" + "}")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(result ->

        mockMvc
            .perform(post("/users/{email}/changePassword", account.getEmail()).content("{\n" + "\"oldPassword\": \""
                + (account.getPassword() + "123") + "\",\n" + "\"newPassword\": \"" + newPassword + "\"\n" + "}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()).andExpect(status().reason("Old password did not match the record")));
  }
  
  @Test
  public void testChangePasswordWrongBodyOldPassword() throws Exception {
    Account account = TestUtilities.createAccount(1);
    String newPassword = "brandNew";

    mockMvc
        .perform(post("/users/{email}/changePassword", account.getEmail()).content("{\n" + "\"oldpassword\": \""
            + account.getPassword() + "\",\n" + "\"newPassword\": \"" + newPassword + "\"\n" + "}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason("Old password cannot be null"));
  }
  
  @Test
  public void testChangePasswordWrongBodyNewPassword() throws Exception {
    Account account = TestUtilities.createAccount(1);
    String newPassword = "brandNew";

    mockMvc
        .perform(post("/users/{email}/changePassword", account.getEmail()).content("{\n" + "\"oldPassword\": \""
            + account.getPassword() + "\",\n" + "\"newpassword\": \"" + newPassword + "\"\n" + "}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason("New password cannot be null"));
  }


    @Test
    public void testChangeAccountEmail() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String newEmail = "newEmail@mail.com";

        // Create the account to be tested
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Change the account's email
        mockMvc.perform(put("/account/changeEmail")
                .param("oldEmail", accountCreateDto.getEmail())
                .param("newEmail", newEmail)
                .param("password", accountCreateDto.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"accountName1\",\"email\":\"newEmail@mail.com\",\"password\":\"password1\"}"));
    }


    @Test
    public void testChangeAccountEmailInvalidEmail() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String newEmail = "This is an invalid email";

        // Create the account to be tested
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Change the account's email
        mockMvc.perform(put("/account/changeEmail")
                .param("oldEmail", accountCreateDto.getEmail())
                .param("newEmail", newEmail)
                .param("password", accountCreateDto.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This is not a valid email address"));
    }


    @Test
    public void testChangeAccountEmailInvalidAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String newEmail = "newEmail@mail.com";

        // Do not post the account and try to change its email address
        mockMvc.perform(put("/account/changeEmail")
                .param("oldEmail", accountCreateDto.getEmail())
                .param("newEmail", newEmail)
                .param("password", accountCreateDto.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This account does not exist"));
    }


    @Test
    public void testDeleteAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        // Create an account
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Log the account in
        mockMvc.perform(get("/login")
                .param("email", account.getEmail())
                .param("password", account.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Delete the account
        mockMvc.perform(delete("/deleteAccount/" + account.getEmail())
                .param("password", account.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteAccountWrongPassword() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        // Create an account
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Log the account in
        mockMvc.perform(get("/login")
                .param("email", account.getEmail())
                .param("password", account.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Delete the account
        mockMvc.perform(delete("/deleteAccount/" + account.getEmail())
                .param("password", "wrongPassword")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Incorrect Password Provided"));
    }


    @Test
    public void testDeleteAccountWrongEmail() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        // Create an account
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" +accountCreateDto.getName()+ "\",\n" +
                "\"email\": \"" +accountCreateDto.getEmail()+ "\",\n" +
                "\"password\": \"" +accountCreateDto.getPassword()+ "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Log the account in
        mockMvc.perform(get("/login")
                .param("email", account.getEmail())
                .param("password", account.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Delete the account
        mockMvc.perform(delete("/deleteAccount/" + "wrongEmail@mail.ca")
                .param("password", account.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This account does not exist"));
    }

    @Test
    public void testAssignScoreToAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String score = "1";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assign the "score" String to the account and verify that the output is as expected
        mockMvc.perform(put("/account/email/score")
                .param("email", accountCreateDto.getEmail())
                .param("score", score)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"email\":\"accountEmail1@a.ca\",\"name\":\"accountName1\",\"password\":\"password1\",\"score\":1,\"level\":0,\"admin\":false,\"verified\":false,\"loggedIn\":false}"));
    }


    @Test
    public void testAssignScoreToAccountInvalidNumber() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String score = "notANumber";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assign the invalid "score" String to the account and verify that an exception is thrown
        mockMvc.perform(put("/account/email/score")
                .param("email", accountCreateDto.getEmail())
                .param("score", score)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("The score is not a number."));
    }

    @Test
    public void testAssignScoreToAccountWrongEmail() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String score = "1";
        String wrongEmail = "wrongEmail";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assign the invalid "score" String to the account and verify that an exception is thrown
        mockMvc.perform(put("/account/email/score")
                .param("email", wrongEmail)
                .param("score", score)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This account does not exist"));
    }

    @Test
    public void testAssignRankToAccount() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String rank = "1";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assign the "rank" String to the account and verify that the output is as expected
        mockMvc.perform(put("/account/email/rank")
                        .param("email", accountCreateDto.getEmail())
                        .param("rank", rank)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"email\":\"accountEmail1@a.ca\",\"name\":\"accountName1\",\"password\":\"password1\",\"score\":0,\"level\":1,\"admin\":false,\"verified\":false,\"loggedIn\":false}"));
    }

    @Test
    public void testAssignRankToAccountInvalidNumber() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String rank = "notANumber";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assign the invalid "rank" String to the account and verify that an exception is thrown
        mockMvc.perform(put("/account/email/rank")
                        .param("email", accountCreateDto.getEmail())
                        .param("rank", rank)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("The rank is not a number."));
    }

    @Test
    public void testAssignRankToAccountWrongEmail() throws Exception {
        Account account = TestUtilities.createAccount(1);
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName(account.getName());
        accountCreateDto.setEmail(account.getEmail());
        accountCreateDto.setPassword(account.getPassword());

        String rank = "1";
        String wrongEmail = "wrongEmail";

        // Create an account and post it to the database
        mockMvc.perform(post("/createAccount").content("{\n" +
                                "\"name\": \"" + accountCreateDto.getName() + "\",\n" +
                                "\"email\": \"" + accountCreateDto.getEmail() + "\",\n" +
                                "\"password\": \"" + accountCreateDto.getPassword() + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assign the invalid "score" String to the account and verify that an exception is thrown
        mockMvc.perform(put("/account/email/rank")
                        .param("email", wrongEmail)
                        .param("rank", rank)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This account does not exist"));
    }
}