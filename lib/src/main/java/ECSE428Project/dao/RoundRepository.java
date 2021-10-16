package ECSE428Project.dao;

import ECSE428Project.model.Round;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends CrudRepository<Round, String> {
}
