package ECSE428Project.dao;

import ECSE428Project.model.AdminConfig;
import ECSE428Project.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, String> {
	List<Match> findAll();
	
}
