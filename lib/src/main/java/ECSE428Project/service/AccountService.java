package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account createAccount(String name, String email, String password) {

        // Create a new account with the input id, name, and email
        Account account = new Account(name, email, password, false, false, 0, 0);

        // Check if the email is well formed
        if(!VALID_EMAIL_REGEX.matcher(email).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not a valid email address");
        }

        // Check that the account does not already exist in the database
        if(accountRepository.findById(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists");
        }

        account = accountRepository.save(account);
        return account;
    }
    
  @Transactional
  public Account changePassword(String email, String oldPass, String newPass) {
    Optional<Account> opt = accountRepository.findById(email);
    Account account;
    
    if (opt.isPresent()) {
      account = opt.get();
      
      //if the oldpassword is correct, we make the change, otherwise the password stays the same
      if (account.getPassword().equals(oldPass)) {
        account.setPassword(newPass);
        account = accountRepository.save(account);
      }

    } else {
      //if no account with the id exists, return null
      return null;
    }

    return account;

  } 
  
  /**
   * Validates that an email and password are associated to an existing account
   * @param email the email of the account
   * @param password the password of the account
   * @return true if there is an account with the given email and password. False otherwise.
   */
  @Transactional
  public boolean validatePassword(String email, String password) {
    Optional<Account> optAccount = accountRepository.findById(email);
    if(optAccount.isEmpty()) {
      return false;
    }
    return optAccount.get().getPassword().equals(password);
  }




    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
}
