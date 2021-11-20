package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;


@Service
public class LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account profileLogin(String email, String password) {

        if (email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credentials cannot be empty! Please Enter Valid Credentials");
        }

        Account profile = accountRepository.findAccountByEmail(email);

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account associated to the provided credentials.");
        }

        // Checking if password is correct and account isn't logged in
        if (password.equals(profile.getPassword()) && !profile.isLoggedIn()) {
            profile.setLoggedIn(true);
            accountRepository.save(profile);
            return profile;

        } else if (profile.isLoggedIn()) {
            throw new IllegalArgumentException("Already Logged In.");
        } else if (!password.equals(profile.getPassword())) {
            throw new IllegalArgumentException("Password did not match records.");
        }
        else {
            return null;
        }
    }








}
