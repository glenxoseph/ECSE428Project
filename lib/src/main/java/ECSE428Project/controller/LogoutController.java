package ECSE428Project.controller;

import ECSE428Project.dto.AccountDto;
import ECSE428Project.model.Account;
import ECSE428Project.service.LogoutService;
//import ECSE428Project.controller.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RestController
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    //Logging out
	@GetMapping(value = { "/logout", "/logout/" })
	public AccountDto logoutProfile(@RequestParam("email") String email)
            throws ResponseStatusException {

        if (!logoutService.profileLogout(email).isLoggedIn()) {
			return convertToDTO(email, false);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Logout failed.");
		}
	}

    //This DTO method can be reused across multiple controller classes
    private AccountDto convertToDTO(String email, boolean isLoggedIn) {
        AccountDto profileDTO = new AccountDto();
        profileDTO.setEmail(email);
        profileDTO.setLoggedIn(isLoggedIn);
        return profileDTO;
    }
}
