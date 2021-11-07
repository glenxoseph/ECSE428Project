package ECSE428Project.dao;

import ECSE428Project.model.AdminConfig;
import ECSE428Project.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, String> {
	
	Match getAllMatches();
	
}
