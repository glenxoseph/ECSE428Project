package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account profileLogin(String email, String password) throws ResponseStatusException {

        if (email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credentials cannot be empty! Please Enter Valid Credentials");
        }
        Optional<Account> optAccount = accountRepository.findById(email);
        Account profile;

        // Check that the account exists in the database
        if (optAccount.isPresent()) {
            profile = optAccount.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No account associated to the provided credentials");
        }

        //Account profile = accountRepository.findAccountByEmail(email);

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account associated to the provided credentials");
        }

        // Checking if password is correct and account isn't logged in
        if (password.equals(profile.getPassword()) && !profile.isLoggedIn()) {
            profile.setLoggedIn(true);
            profile = accountRepository.save(profile);
            return profile;

        } else if (profile.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Logged In");
        } else if (!password.equals(profile.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password did not match records");
        }
        else {
            return null;
        }
    }
}