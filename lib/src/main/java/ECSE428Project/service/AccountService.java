package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.LeaderboardRepository;
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

    @Autowired
    private LeaderboardRepository leaderboardRepository;

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
    public Account getAccount(String email) {
        Optional<Account> optionalAccount = accountRepository.findById(email);

        if (!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account associated to this email");
        } else {
            return optionalAccount.get();
        }
    }

    @Transactional
    public void deleteAccount(String email, String password) {
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account associated to this email");
        }else{
            // Delete a new account with the input id
            Optional<Account> opt = accountRepository.findById(email);
            Account account;

            if (opt.isPresent()) {
                account = opt.get();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This account does not exist");
            }

            if (!account.isLoggedIn()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Logged in");
            }

            if(!account.getPassword().equals(password)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Password Provided");

            }

            leaderboardRepository.deleteLeaderboardByAccountEmail(account.getEmail());
            accountRepository.delete(account);
        }



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


  @Transactional
  public Account changeAccountEmail(String oldEmail, String newEmail, String password) {
        Optional<Account> opt = accountRepository.findById(oldEmail);
        Account account;
        Account newAccount;

        // Check if the new email is well formed
        if(!VALID_EMAIL_REGEX.matcher(newEmail).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not a valid email address");
        }

        if (opt.isPresent()) {
            account = opt.get();

            // The password needs to be checked for the email to be changed
            if (account.getPassword().equals(password)) {

                // Create a new account with the same properties as the old account but with a different email
                newAccount = new Account(account.getName(), newEmail, account.getPassword(), account.isVerified(),
                        account.isLoggedIn(), account.getScore(), account.getLevel());

                // Save the new account to the database
                newAccount = accountRepository.save(newAccount);

                // Delete the account with the old email
                accountRepository.delete(account);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is incorrect");
            }

        } else {
            // If the account does not exist in the repository, throw an exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account does not exist");
        }

        return newAccount;
    }
  
  @Transactional
  public boolean validatePassword(String email, String password) {
	  Optional<Account> optAccount = accountRepository.findById(email);
	  if(optAccount.isPresent() && optAccount.get().getPassword().equals(password)) {
		  return true;
	  }
	  return false;
  }

  @Transactional
  public Account assignScoreToAccount(String email, int score) {
      Optional<Account> optAccount = accountRepository.findById(email);
      Account account;

      // Check that the account exists in the database
      if (optAccount.isPresent()){
          account = optAccount.get();

          int oldScore = account.getScore();
          int newScore = oldScore + score;

          // Set the new score to the account
          account.setScore(newScore);
          account = accountRepository.save(account);

      } else {
          // Throw exception if the account doesn't exist
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account does not exist");
      }

      return account;
  }

    @Transactional
    public Account assignRankToAccount(String email, int rank) {
        Optional<Account> optAccount = accountRepository.findById(email);
        Account account;

        // Check that the account exists in the database
        if (optAccount.isPresent()){
            account = optAccount.get();

            // Set the new rank to the account
            account.setLevel(rank);
            account = accountRepository.save(account);

        } else {
            // Throw exception if the account doesn't exist
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account does not exist");
        }

        return account;
    }

    @Transactional
    public void logoutAllAccounts() {
        Iterable<Account> accountList = accountRepository.findAll();

        for(Account account : accountList) {
            account.setLoggedIn(false);
            accountRepository.save(account);
        }
    }


    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
}
