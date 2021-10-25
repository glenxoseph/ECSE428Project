package ECSE428Project.controller;

import ECSE428Project.dto.AccountCreateDto;
import ECSE428Project.dto.AccountDto;
import ECSE428Project.model.Account;
import ECSE428Project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static ECSE428Project.controller.DataTransferObjects.convertToDto;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class AccountController {

    @Autowired
    public AccountService accountService;


    /**
     * Creates an account with an email, a name, and a password
     * @param accountCreateDto an accountCreateDto
     * return an accountDto
     * @throws ResponseStatusException if any part of the accountCreateDto is missing, or if the email is not correctly formed
     */
    @PostMapping(path = "/createAccount")
    public AccountDto createAccount(@RequestBody AccountCreateDto accountCreateDto) throws ResponseStatusException {

        // Verify that the input account is not null and that the input fields are correctly formed
        if(!validateAccountCreateDto(accountCreateDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account");
        }

        // Create an account with the input attributes
        Account account = accountService.createAccount(accountCreateDto.getName(), accountCreateDto.getEmail(), accountCreateDto.getPassword());

        return convertToDto(account);
    }
    
  @PostMapping(path = { "/users/{email}/changePassword", "/users/{email}/changePassword/" })
  public AccountDto changePassword(@PathVariable String email, @RequestBody Map<String, String> json)
      throws ResponseStatusException {

    if (json == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old and new password cannot be null");
    }

    String oldPass = json.get("oldPassword");
    String newPass = json.get("newPassword");

    if (oldPass == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password cannot be null");
    }

    if (newPass == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be null");
    }

    Account account = accountService.changePassword(email, oldPass, newPass);

    if (account == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account associated to the email " + email + " exists");
    }
    if (!account.getPassword().equals(newPass)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password did not match the record");
    }
    return convertToDto(account);
  }

    /**
     * This method updates an account's email
     * @param oldEmail the old email associated to the account
     * @param newEmail the new email to be associated to the account
     * @param password the account's password
     * @return updated account
     * @throws ResponseStatusException if the account does not exist
     */
  @PutMapping(path = {"/account/changeEmail", "/account/changeEmail/"})
  public AccountDto updateAccountWithNewEmail(@RequestParam("oldEmail") String oldEmail, @RequestParam("newEmail")
          String newEmail, @RequestParam("password") String password) throws ResponseStatusException {

        Account account = accountService.changeAccountEmail(oldEmail, newEmail, password);

        return convertToDto(account);
  }


    private boolean validateAccountCreateDto(AccountCreateDto accountCreateDto) {
        return (accountCreateDto.getEmail() != null
                && accountCreateDto.getEmail().trim().length() > 0
                && accountCreateDto.getName() != null
                && accountCreateDto.getName().trim().length() > 0
                && accountCreateDto.getPassword() != null
                && accountCreateDto.getPassword().trim().length() > 0);
    }
}
