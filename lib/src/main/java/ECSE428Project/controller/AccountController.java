package ECSE428Project.controller;

import ECSE428Project.dto.AccountCreateDto;
import ECSE428Project.dto.AccountDto;
import ECSE428Project.model.Account;
import ECSE428Project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
     */
    @PostMapping(path = "/createAccount")
    public AccountDto createAccount(@RequestBody AccountCreateDto accountCreateDto) throws ResponseStatusException {

        // Verify that the input account is not null and that the input fields are correctly formed
        if(accountCreateDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account cannot be null");
        }

        if(!validateAccountCreateDto(accountCreateDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account");
        }

        // Create an account with the input attributes
        Account account = accountService.createAccount(accountCreateDto.getName(), accountCreateDto.getEmail(), accountCreateDto.getPassword());

        return convertToDto(account);
    }


    /**
     * Deletes an account
     */
    @DeleteMapping(value = { "/deleteAccount/{email}", "/deleteAccount/{email}/" })
    public void deleteAccount(@PathVariable("email") String email,@RequestParam("password") String password)  throws IllegalArgumentException {
       accountService.deleteAccount(email,password);
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

    /**
     * This method updates the score associated to an account
     * @param email the email associated to the account
     * @param scoreString the score that is going to be assigned to the account as a String
     * @return updated account
     * @throws ResponseStatusException if the score is not a number
     */
    @PutMapping(path = "/account/email/score")
    public AccountDto assignScoreToAccount(@RequestParam("email") String email, @RequestParam("score") String scoreString)
            throws ResponseStatusException {

        try {
            Double.parseDouble(scoreString);
        } catch (NumberFormatException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The score is not a number.");
        }

        return null;
        // These lines will be uncommented when the service method is added
        // Account account = accountService.assignScoreToAccount(email, Double.parseDouble(scoreString));
        // return convertToDto(account);
    }

    /**
     * This method updates the rank associated to an account
     * @param email the email associated to the account
     * @param rankString the rank that is going to be assigned to the account as a String
     * @return updated account
     * @throws ResponseStatusException if the rank is not a number
     */
    @PutMapping(path = "/account/email/rank")
    public AccountDto assignRankToAccount(@RequestParam("email") String email, @RequestParam("rank") String rankString)
            throws ResponseStatusException {
        try {
            Integer.parseInt(rankString);
        } catch (NumberFormatException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The rank is not a number.");
        }

        return null;
        // These lines will be uncommented when the service method is added
        //Account account = accountService.assignRankToAccount(email, Integer.parseInt(rankString);
        //return convertToDto(account);
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
