package ECSE428Project.service;

import ECSE428Project.dao.QuestionRepository;
import ECSE428Project.model.Question;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Transactional
    public List<Question> getAllQuestions(){
    	List<Question> questions = new ArrayList<>();
    	questionRepository.findAll().iterator().forEachRemaining(question -> questions.add(question));
    	return questions;
    }
    
    @Transactional
    public Question getQuestion(String id) {
    	return questionRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public Question createQuestion(Question question) {
    	return questionRepository.save(question);
    }
}
