package ECSE428Project.controller;


import ECSE428Project.dto.AccountDto;
import ECSE428Project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    // Verifying login
    @GetMapping(value = { "/login", "/login/" })
    public AccountDto verifyCredentials(@RequestParam("email") String email, @RequestParam("password") String password)
            throws IllegalArgumentException {
        if (service.profileLogin(email, password).isLoggedIn()) {
            AccountDto profileDTO = convertToDTO(email, true);
            return profileDTO;
        } else {
            throw new IllegalArgumentException("Login Failed"); // Won't really happen
        }
    }


    // For logging in
    private AccountDto convertToDTO(String email, boolean isLoggedIn) {
        AccountDto profileDTO = new AccountDto();
        profileDTO.setEmail(email);
        profileDTO.setLoggedIn(isLoggedIn);
        return profileDTO;
    }
}
