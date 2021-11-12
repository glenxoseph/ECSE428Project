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
public class LogoutService {

    @Autowired
    private AccountRepository accountRepository;


    //It would be good to implement a "find current logged in account" method
    //Could be implemented in the library class
/*
    //Retrieve all account entities saved in the accountRepository
    List<Account> savedAccounts = accountRepository.findAll();

    if(savedAccounts.isEmpty()) {
        throw new Exception...
    }
    //Retrieve the first entity in the account list
    Account dummyAccount = savedAccounts.get(0);
*/


    @Transactional
    public Account profileLogout(String email) {

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be empty! Please Enter Valid Credentials");
        }

        Account profile = accountRepository.findAccountByEmail(email);
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