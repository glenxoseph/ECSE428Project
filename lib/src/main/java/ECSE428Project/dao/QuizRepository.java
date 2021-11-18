package ECSE428Project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ECSE428Project.model.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, String> {
	Quiz findByName(String name);
}