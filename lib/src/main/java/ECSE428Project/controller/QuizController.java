package ECSE428Project.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ECSE428Project.dto.QuizDto;
import ECSE428Project.model.Quiz;
import ECSE428Project.service.QuizService;

@CrossOrigin(origins = "*")
@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;

	@GetMapping(path = { "/quiz/all", "/quiz/all/" })
	public List<QuizDto> getAllQuizzes() throws ResponseStatusException {
		List<QuizDto> list = new ArrayList<>();
		quizService.getAllQuizzes().iterator().forEachRemaining(quiz ->list.add(QuizDto.toDto(quiz)));
		return list;
	}

	@GetMapping(path = { "/quiz/all/names", "/quiz/all/names" })
	public List<String> getAllQuizzesNames() throws ResponseStatusException {
		return quizService.getAllQuizzesNames();
	}

	@GetMapping(path = { "/quiz/{name}", "/quiz/{name}/" })
	public QuizDto getQuiz(@PathVariable String name) throws ResponseStatusException {
		name = name.replace("%20", " ");
		Quiz quiz = quizService.getQuizByName(name);
		if (quiz == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No quiz with name " + name + " could be found.");
		}
		QuizDto quizDto = QuizDto.toDto(quiz);
		return quizDto;
	}

	@PostMapping(path = { "/quiz/create", "/quiz/create/" })
	public List<QuizDto> createQuizzes(@RequestBody List<Quiz> quizzes) throws ResponseStatusException {
		List<QuizDto> dtoQuizzes = new ArrayList<>();
		quizService.createQuizzes(quizzes).iterator().forEachRemaining(quiz -> dtoQuizzes.add(QuizDto.toDto(quiz)));
		return dtoQuizzes;
	}
}
