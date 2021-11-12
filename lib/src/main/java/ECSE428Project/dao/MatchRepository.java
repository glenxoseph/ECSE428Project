package ECSE428Project.dao;

import ECSE428Project.model.Match;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, String> {
	Set<Match> findByAccounts_Email(String email);
}
