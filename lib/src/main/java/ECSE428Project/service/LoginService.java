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
    
    @Autowired
    private AdminConfigService adminService;

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

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account associated to the provided credentials");
        }

        boolean isBanned = adminService.getBannedEmails().contains(profile.getEmail());
        // Checking if password is correct and account isn't logged in
        if (password.equals(profile.getPassword()) && !profile.isLoggedIn() && !isBanned) {
            profile.setLoggedIn(true);
            profile = accountRepository.save(profile);
            return profile;

        } else if (profile.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Logged In");
        } else if (!password.equals(profile.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password did not match records");
        } else if(isBanned) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email associated to this account is banned");
        }
        else {
            return null;
        }
    }
}