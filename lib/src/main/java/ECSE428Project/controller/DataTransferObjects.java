package ECSE428Project.controller;

import ECSE428Project.dto.AccountDto;
import ECSE428Project.model.Account;

public class DataTransferObjects {

    public static AccountDto convertToDto(Account account) {

        // Create a new account Data Transfer Object with the attributes of the input account
        AccountDto accountDto = new AccountDto();
        accountDto.setName(account.getName());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(account.getPassword());
        accountDto.setLevel(account.getLevel());
        accountDto.setScore(account.getScore());

        return accountDto;

    }
}
