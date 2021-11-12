package ECSE428Project.controller;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LogoutControllerTest {

    //Variables used to create dummy account entities in the AccountRepository
    private static final int seed = 1;
    private static final String accountName = "accountName"+seed;
    private static final String accountEmail = "accountEmail"+seed+"@a.ca";
    private static final String accountPassword = "password"+seed;

    private static final String loggedInEmail = "wrongEmail@a.ca";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    /*
    Create dummy test accounts in the Repo using the Account Class
    */
    @BeforeEach
    public void createDummyAccount() {

        //empty the repository
        accountRepository.deleteAll();

        // Create a new account with the input password, name, and email
        Account loggedInAccount = new Account("wrongName", loggedInEmail, "wrongPassword", false, true, 0, 0);
        loggedInAccount = accountRepository.save(loggedInAccount);

        // Create a new account with the input id, name, and email
        Account testAccount = new Account(accountName, accountEmail, accountPassword, false, false, 0, 0);

        //Save to the dummy account into the accountRepository
        testAccount = accountRepository.save(testAccount);

        //Validate the accountRepository is not empty
        if(accountRepository.count() == 0) {
            fail("Test Aborted: The Account Repository is empty. Entity Count = "+ accountRepository.count());
        }
        //Validate that the saved Account entity has: loggedIn == false
        Assertions.assertFalse(testAccount.isLoggedIn(),
                "Test Aborted: The dummy Account was not created successfully: LoggedIn = false");
    }
    /*
   Delete dummy test accounts in the accountRepository
   */
    @AfterEach
    public void clearTestingAccounts() {
        accountRepository.deleteAll();
        assertEquals(0, accountRepository.count());
    }


    @Test
    public void testLogoutSuccessful() throws Exception {

        Account profile = accountRepository.findAccountByEmail(loggedInEmail);
        System.out.println("At the start of the test, the Account is logged in? " + profile.isLoggedIn());

        mockMvc.perform(get("/logout")
                .param("email", profile.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        profile = accountRepository.findAccountByEmail(loggedInEmail);
        System.out.println("At the end of the test, the Account is logged in? " + profile.isLoggedIn());
    }


    @Test
    public void testAlreadyLoggedOut() throws Exception {

        Account profile = accountRepository.findAccountByEmail(accountEmail);
        System.out.println("At the start of the test, the Account is logged in? " + profile.isLoggedIn());

        mockMvc.perform(get("/logout")
                        .param("email", profile.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Profile is not logged in. LogoutProfile service call failed."));

        profile = accountRepository.findAccountByEmail(accountEmail);
        System.out.println("At the end of the test, the Account is logged in? " + profile.isLoggedIn());
    }
}
