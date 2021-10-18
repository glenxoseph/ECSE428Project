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


    //Do we need to implement a "find current logged in account" method?
    //Could be implemented in the library class


    @Transactional
    public Account profileLogout(String email) {

        Account profile = accountRepository.findAccountByEmail(email);

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account assosciated to the provided credentials");
        }

        //Account must be logged in
        if (profile.isLoggedIn()) {
            profile.setLoggedIn(false);
            accountRepository.save(profile);
            return profile;

        } else if (!profile.isLoggedIn()) {
            throw new IllegalArgumentException("Profile is not logged in");
        } else {
                return null;
        }
    }
}