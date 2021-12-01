package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.TestUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogoutServiceTest {

    @Autowired
    private LogoutService logoutService;

    @MockBean
    private AccountRepository accountRepository;


    @BeforeEach
    public void setMockSaveOutput() {
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testLogoutAccount() {
        // Create an account and variables and log the account in
        Account testAccount = TestUtilities.createAccount(1);
        testAccount.setLoggedIn(true);

        // Mock repository call to mock there already being a value in the database with this email
        when(accountRepository.findById(testAccount.getEmail())).thenReturn(Optional.of(testAccount));

        // Call the method to log the account out
        Account account = logoutService.profileLogout(testAccount.getEmail());

        // Verify that the account is logged out and that the correct calls were made
        assertFalse(account.isLoggedIn());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    public void testLogoutAccountNotLoggedIn() {
        // Create an account and variables and log the account in
        Account testAccount = TestUtilities.createAccount(1);
        testAccount.setLoggedIn(false);

        // Mock repository call to mock there already being a value in the database with this email
        when(accountRepository.findById(testAccount.getEmail())).thenReturn(Optional.of(testAccount));

        // Verify that an error is thrown
        assertThrows(ResponseStatusException.class, () ->
                        logoutService.profileLogout(testAccount.getEmail()));
        verify(accountRepository, never()).save(any(Account.class));
    }
}
