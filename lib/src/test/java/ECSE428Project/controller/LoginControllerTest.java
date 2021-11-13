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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginControllerTest {

    //Variables used to create dummy account entities in the AccountRepository
    private static final int seed = 1;
    private static final String accountName = "accountName"+seed;
    private static final String accountEmail = "accountEmail"+seed+"@a.ca";
    private static final String accountPassword = "password"+seed;

    private static final String wrongEmail = "wrongEmail@a.ca";

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
        Account errorAccount = new Account("wrongName", wrongEmail, "wrongPassword", false, true, 0, 0);
        errorAccount = accountRepository.save(errorAccount);

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
    public void testLoginSuccessful() throws Exception {

        Account profile = accountRepository.findAccountByEmail(accountEmail);
        boolean before = profile.isLoggedIn();
        System.out.println("At the start of the test, the Account is logged in? " + before);

        mockMvc.perform(get("/login")
                .param("email", profile.getEmail())
                .param("password", profile.getPassword())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        profile = accountRepository.findAccountByEmail(accountEmail);
        boolean after = profile.isLoggedIn();
        System.out.println("At the end of the test, the Account is logged in? " + after);

        assertNotEquals(before, after, "LoginController testLoginSuccessful failed. User login status was not changed.");
    }


    @Test
    public void testAlreadyLoggedIn() throws Exception {

        Account profile = accountRepository.findAccountByEmail(wrongEmail);
        boolean before = profile.isLoggedIn();
        System.out.println("At the start of the test, the Account is logged in? " + before);

        mockMvc.perform(get("/login")
                        .param("email", profile.getEmail())
                        .param("password", profile.getPassword())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Already Logged In"));

        profile = accountRepository.findAccountByEmail(wrongEmail);
        boolean after = profile.isLoggedIn();
        System.out.println("At the end of the test, the Account is logged in? " + after);

        assertEquals(before, after, "LoginController testAlreadyLoggedIn failed. User login status was changed.");
    }
}
