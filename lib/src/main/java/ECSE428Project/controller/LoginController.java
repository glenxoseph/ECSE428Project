package ECSE428Project.controller;


import ECSE428Project.dto.AccountDto;
import ECSE428Project.service.AdminConfigService;
import ECSE428Project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    @Autowired
    private AdminConfigService adminConfigService;

    // Verifying login
    @GetMapping(value = { "/login", "/login/" })
    public AccountDto verifyCredentials(@RequestParam("email") String email, @RequestParam("password") String password)
            throws ResponseStatusException {

        if (service.profileLogin(email, password).isLoggedIn()) {
            AccountDto profileDTO = convertToDTO(email, true, adminConfigService.isAdmin(email, password));
            return profileDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login Failed");
        }
    }


    // For logging in
    private AccountDto convertToDTO(String email, boolean isLoggedIn, boolean isAdmin) {
        AccountDto profileDTO = new AccountDto();
        profileDTO.setEmail(email);
        profileDTO.setLoggedIn(isLoggedIn);
        profileDTO.setIsAdmin(isAdmin);
        return profileDTO;
    }
}
