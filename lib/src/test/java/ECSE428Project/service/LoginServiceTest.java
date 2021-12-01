package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginServiceTest {

    //Variables used to create dummy account entities in the AccountRepository
    private static final int seed = 1;
    private static final String accountName = "accountName"+seed;
    private static final String accountEmail = "accountEmail"+seed+"@a.ca";
    private static final String accountPassword = "password"+seed;

    private static final String wrongEmail = "wrongEmail@a.ca";

    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private AdminConfigService adminService;
    /*
    Create dummy test accounts in the Repo using the Account Class
    */
    @BeforeEach
    public void createDummyAccount() {

        // Create a new account with the input password, name, and email
        Account wrongAccount = new Account("wrongName", wrongEmail, "wrongPassword", false, false, 0, 0);
        accountRepository.save(wrongAccount);

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

    @Test
    public void testBannedLoginRequest() {

        System.out.println(accountRepository.count()+" Entities exist in the account repository.");
        List<String> bannedEmails = new ArrayList<>();
        bannedEmails.add(accountEmail);
        when(adminService.getBannedEmails()).thenReturn(bannedEmails);
        final List<Account> innerAccount = new ArrayList<>();
        //Call to loginService class method
        assertThrows(ResponseStatusException.class, () -> innerAccount.add(loginService.profileLogin(accountEmail, accountPassword)), 
        		"The email associated to this account is banned");
        assertTrue(innerAccount.size() == 0,
                "LoginAccount Service test failed: Account was not successfully logged in.");
    }
    
    @Test
    public void testLoginRequest() {

        System.out.println(accountRepository.count()+" Entities exist in the account repository.");

        //Call to loginService class method
        Account dummyAccount = loginService.profileLogin(accountEmail, accountPassword);

        if(dummyAccount == null) {
            fail("Test Aborted: The Account object returned from the service class is null.");
        }
        //Validate the fields of the returned account saved in the Repo
        else if(!(dummyAccount.getName().equals(accountName)
                && dummyAccount.getEmail().equals(accountEmail)
                && dummyAccount.getPassword().equals(accountPassword))) {
            fail("Test Aborted: The dummy Account retrieved is invalid.");
        }

        //Verify that the loginService.profileLogin method call logs in the dummy account.
        assertTrue(dummyAccount.isLoggedIn(),
                "LoginAccount Service test failed: Account was not successfully logged in.");
    }
}