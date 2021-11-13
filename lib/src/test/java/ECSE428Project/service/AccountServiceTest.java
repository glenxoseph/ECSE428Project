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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

  @Test
  public void testChangePassword() {
    String name = "accountName1", email = "max@hotmail.com", oldPassword = "password1", newPassword = "password2";
    Account account = new Account();
    account.setName(name);
    account.setEmail(email);
    account.setPassword(oldPassword);

    // mock repository call to mock there already being a value in the database with
    // this email
    when(accountRepository.findById(email)).thenReturn(Optional.of(account));
    account = accountService.changePassword(email, oldPassword, newPassword);
    assertEquals(email, account.getEmail());
    assertEquals(newPassword, account.getPassword());
    verify(accountRepository).save(any(Account.class));
  }

  @Test
  public void testChangePasswordWrongEmail() {
    String name = "accountName1", email = "this_email_doesnt_exist@hotmail.com", oldPassword = "password1", newPassword = "password2";
    Account account = new Account();
    account.setName(name);
    account.setEmail(email);
    account.setPassword(oldPassword);

    // mock repository call to mock there not being a value in the database with
    // this email
    when(accountRepository.findById(email)).thenReturn(Optional.empty());
    account = accountService.changePassword(email, oldPassword, newPassword);
    assertNull(account);
    verify(accountRepository, never()).save(any(Account.class));
  }

  @Test
  public void testChangePasswordWrongPassword() {
    String name = "accountName1", email = "max@hotmail.com", oldPassword = "password1", newPassword = "password2",
        invalidPassword = "Password1";
    Account account = new Account();
    account.setName(name);
    account.setEmail(email);
    account.setPassword(oldPassword);

    // mock repository call to mock there already being a value in the database with
    // this email
    when(accountRepository.findById(email)).thenReturn(Optional.of(account));
    account = accountService.changePassword(email, invalidPassword, newPassword);
    assertEquals(email, account.getEmail());
    assertEquals(oldPassword, account.getPassword());
    verify(accountRepository, never()).save(any(Account.class));
  }


    @Test
    public void testChangeAccountEmail() {
        String oldEmail = "oldEmail@mail.com", newEmail = "newEmail@mail.com", password = "password", name = "name";
        Account account = new Account();
        account.setName(name);
        account.setEmail(oldEmail);
        account.setPassword(password);

        // mock repository call to mock there already being a value in the database with
        // this email
        when(accountRepository.findById(oldEmail)).thenReturn(Optional.of(account));

        // Call the service method to change the account's email
        account = accountService.changeAccountEmail(oldEmail, newEmail, password);

        // Verify that the method returns the expected results
        assertEquals(newEmail, account.getEmail());
        assertEquals(name, account.getName());
        verify(accountRepository).save(any(Account.class));
    }


    @Test
    public void testChangeAccountIncorrectPassword() {
        String oldEmail = "oldEmail@mail.com", newEmail = "newEmail@mail.com", password = "password",
                wrongPassword = "wrongPassword", name = "name";
        Account account = new Account();
        account.setName(name);
        account.setEmail(oldEmail);
        account.setPassword(password);

        // mock repository call to mock there already being a value in the database with
        // this email
        when(accountRepository.findById(oldEmail)).thenReturn(Optional.of(account));

        // Verify that, when called, the method throws the expected exception
        assertThrows(ResponseStatusException.class, () ->
                        accountService.changeAccountEmail(oldEmail, newEmail, wrongPassword),
                "The password is incorrect");

        // Verify that no account was saved in the repository when the method was called
        verify(accountRepository, never()).save(any(Account.class));
    }


    @Test
    public void testChangeAccountEmailInvalidEmail() {
        String oldEmail = "oldEmail@mail.com", newEmail = "This is an invalid email", password = "password", name = "name";
        Account account = new Account();
        account.setName(name);
        account.setEmail(oldEmail);
        account.setPassword(password);

        // Mock repository call to mock there already being a value in the database with
        // this email
        when(accountRepository.findById(oldEmail)).thenReturn(Optional.of(account));

        // Verify that, when called, the method throws the expected exception
        assertThrows(ResponseStatusException.class, () ->
                        accountService.changeAccountEmail(oldEmail, newEmail, password),
                "This is not a valid email address");

        // Verify that no account was saved in the repository when the method was called
        verify(accountRepository, never()).save(any(Account.class));
    }


    @Test
    public void testChangeAccountEmailInvalidAccount() {
        String oldEmail = "oldEmail@mail.com", newEmail = "newEmail@mail.com", password = "password";

        // Mock repository call to mock the fact that there is no account with that email address
        when(accountRepository.findById(oldEmail)).thenReturn(Optional.empty());

        // Verify that, when called, the method throws the expected exception
        assertThrows(ResponseStatusException.class, () ->
                        accountService.changeAccountEmail(oldEmail, newEmail, password),
                "This account does not exist");

        // Verify that no account was saved in the repository when the method was called
        verify(accountRepository, never()).save(any(Account.class));
    }


    @Test
    public void DeleteAccountSuccessfully() {
        String name = "accountName1", email = "salmanmesamali@hotmail.com", password = "password1";
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        account.setPassword(password);
        account.setLoggedIn(true);

        // mock repository call to mock there already being a value in the database with
        // this email
         when(accountRepository.findById(email)).thenReturn(Optional.of(account));

        accountService.deleteAccount(email,password);
        verify(accountRepository).delete(any(Account.class));
    }


    @Test
    public void deleteAccountWrongPassword() {
        String name = "accountName1", email = "salmanmesamali@hotmail.com", password = "password1";
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        account.setPassword(password);
        account.setLoggedIn(true);

        // mock repository call to mock there already being a value in the database with
        // this email
        when(accountRepository.findById(email)).thenReturn(Optional.of(account));

        // Verify that an exception is thrown when the wrong password is provided
        assertThrows(ResponseStatusException.class, () ->
                        accountService.deleteAccount(email, "wrongPassword"),
                "Incorrect Password Provided");
    }

    @Test
    public void deleteAccountWrongEmail() {
        String name = "accountName1", email = "salmanmesamali@hotmail.com", password = "password1";
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        account.setPassword(password);
        account.setLoggedIn(true);

        // mock repository call to mock there already being a value in the database with
        // this email
        when(accountRepository.findById(email)).thenReturn(Optional.of(account));

        // Verify that an exception is thrown when the wrong password is provided
        assertThrows(ResponseStatusException.class, () ->
                        accountService.deleteAccount("wrongEmail", password),
                "j");
    }
}
