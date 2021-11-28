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
public class LogoutService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account profileLogout(String email) {

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be empty! Please Enter Valid Credentials");
        }
        Optional<Account> optAccount = accountRepository.findById(email);
        Account profile;

        // Check that the account exists in the database
        if (optAccount.isPresent()) {
            profile = optAccount.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No account associated to the provided credentials");
        }

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account associated to the provided credentials");
        }

        //Account must be logged in
        if (profile.isLoggedIn()) {
            profile.setLoggedIn(false);
            profile = accountRepository.save(profile);
            return profile;

        } else if (!profile.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is not logged in. LogoutProfile service call failed.");
        } else {
            return null;
        }
    }
}