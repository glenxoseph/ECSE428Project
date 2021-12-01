package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogoutServiceTest {

    //Variables used to create dummy account entities in the AccountRepository
    private static final int seed = 1;
    private static final String accountName = "accountName"+seed;
    private static final String accountEmail = "accountEmail"+seed+"@a.ca";
    private static final String accountPassword = "password"+seed;

    private static final String wrongEmail = "wrongEmail@a.ca";

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private AccountRepository accountRepository;

    /*
    Create dummy test accounts in the Repo using the Account Class
    */
    @BeforeEach
    public void createDummyAccount() {

        //empty the repository
        //accountRepository.deleteAll();

        // Create a new "wrong" account with the input password, name, and email
        Account wrongAccount = new Account("wrongName", wrongEmail, "wrongPassword", false, true, 0, 0);
        accountRepository.save(wrongAccount);

        // Create a new account with the input password, name, and email
        Account testAccount = new Account(accountName, accountEmail, accountPassword, false, true, 0, 0);

        //Save to the dummy account into the accountRepository
        testAccount = accountRepository.save(testAccount);

        //Validate the accountRepository is not empty
        if(accountRepository.count() == 0) {
            fail("Test Aborted: The Account Repository is empty. Entity Count = "+ accountRepository.count());
        }
        //Validate that the saved Account entity has: loggedIn == true
        Assertions.assertTrue(testAccount.isLoggedIn(),
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
    public void testLogoutRequest() {

        System.out.println(accountRepository.count()+" Entities exist in the account repository.");

        //Call to logoutService class method
        Account dummyAccount = logoutService.profileLogout(accountEmail);

        if(dummyAccount == null) {
            fail("Test Aborted: The Account object returned from the service class is null.");
        }
        //Validate the fields of the returned account saved in the Repo
        else if(!(dummyAccount.getName().equals(accountName)
                && dummyAccount.getEmail().equals(accountEmail)
                && dummyAccount.getPassword().equals(accountPassword))) {
            fail("Test Aborted: The dummy Account retrieved is invalid.");
        }

        //Verify that the logoutService.profileLogout method call logs out the dummy account.
        assertFalse(dummyAccount.isLoggedIn(),
                "LogoutAccount service test failed: Account was not successfully logged out.");
    }
}
