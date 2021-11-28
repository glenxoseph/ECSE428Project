package ECSE428Project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ECSE428Project.model.Question;
import ECSE428Project.service.QuestionService;

@CrossOrigin(origins = "*")
@RestController
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping(path = { "/question/all", "/question/all/" })
	public List<Question> getAllQuestions() throws ResponseStatusException{
		return questionService.getAllQuestions();
	}
	
	@GetMapping(path = { "/question/{id}", "/question/{id}/" })
	public Question getQuestion(@PathVariable String id) throws ResponseStatusException{
		Question question =  questionService.getQuestion(id);
		if(question == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"No question with such id could be found.");
		}
		return question;
	}
	
	@GetMapping(path = { "/question/create", "/question/create/" })
	public Question createQuestion(@RequestBody Question question) throws ResponseStatusException{
		return questionService.createQuestion(question);
	}
}
