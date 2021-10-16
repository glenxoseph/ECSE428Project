package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.TestUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;


    @BeforeEach
    public void setMockSaveOutput() {
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateAccount() {
        // Create an account and variables
        Account testAccount = TestUtilities.createAccount(1);
        String accountName = "accountName1", accountEmail = "accountEmail1@a.ca", accountPassword = "password1";

        Account account = accountService.createAccount(accountName, accountEmail, accountPassword);

        // Verify that everything is called and returned as expected
        assertEquals(testAccount.getName(), account.getName());
        assertEquals(testAccount.getEmail(), account.getEmail());
        assertEquals(testAccount.getPassword(), account.getPassword());

        verify(accountRepository).save(any(Account.class));
    }


    @Test
    public void testAccountAlreadyExists() {
        // Create an account and variables
        Account testAccount = TestUtilities.createAccount(1);
        String accountName = "accountName1", accountEmail = "accountEmail1@a.ca", accountPassword = "password1";

        // mock repository call to mock there already being a value in the database with this email
        when(accountRepository.findById(accountEmail)).thenReturn(Optional.of(testAccount));

        // Verify that an exception is thrown
        assertThrows(ResponseStatusException.class, () ->
                        accountService.createAccount(accountName, accountEmail, accountPassword),
                "Failed to throw exception from account that already exists");

        // Verify that findById was called. By default, times(1) is used for number of times called
        verify(accountRepository).findById(accountEmail);

        // Verify that accountRepository.save() was never called
        verify(accountRepository, never()).save(any(Account.class));
    }


    @Test
    public void testCreateAccountInvalidEmail() {
        // Create tested variables including an invalid email
        String accountName = "accountName1", accountEmail = "this is not a valid email", accountPassword = "password1";

        // Verify that an exception is thrown
        assertThrows(ResponseStatusException.class, () ->
                        accountService.createAccount(accountName, accountEmail, accountPassword),
                "This is not a valid email address");

        // Verify that accountRepository.save() was never called
        verify(accountRepository, never()).save(any(Account.class));
    }
}
