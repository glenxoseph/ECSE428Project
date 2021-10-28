package ECSE428Project.dao;

import ECSE428Project.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    Account findAccountByEmail(String email);

    //Overrides findAll() method of the CrudRepository to return all entities as a list
    List<Account> findAll();
}
