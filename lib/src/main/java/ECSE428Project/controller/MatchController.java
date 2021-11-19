package ECSE428Project.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ECSE428Project.dto.MatchDto;
import ECSE428Project.dto.PostGameStatDto;
import ECSE428Project.dto.QuestionDto;
import ECSE428Project.model.GameMode;
import ECSE428Project.model.Match;
import ECSE428Project.model.Question;
import ECSE428Project.service.MatchService;

@CrossOrigin(origins = "*")
@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;

	@GetMapping(path = { "/match/history", "/match/history/" })
	public Set<MatchDto> getAllMatches() throws ResponseStatusException {
		Set<Match> matches = matchService.getAllMatch();
		Set<MatchDto> dtos = new HashSet<>();
		matches.iterator().forEachRemaining(match -> dtos.add(MatchDto.toDto(match)));

		return dtos;
	}

	@GetMapping(path = { "/match/history/{email}", "/match/history/{email}/" })
	public Set<MatchDto> getAccountMatches(@PathVariable String email) throws ResponseStatusException {
		Set<Match> matches = matchService.getMatchOfAccount(email);
		Set<MatchDto> dtos = new HashSet<>();
		if (matches == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"No account with the corresponding email can be found.");
		}
		matches.iterator().forEachRemaining(match -> dtos.add(MatchDto.toDto(match)));
		return dtos;
	}

	@PostMapping(path = { "/match/create", "/match/create/" })
	public MatchDto createMatch(@RequestBody List<String> emails,
			@RequestParam(value = "gamemode", defaultValue = "SOLO") GameMode mode,
			@RequestParam(value = "quizName", required = false) String quizName) throws ResponseStatusException {
		Match match = null;
		try {
			match = matchService.createMatch(emails, mode, quizName);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return MatchDto.toDto(match);
	}

	@PostMapping(path = { "/match/quiz/verify", "/match/quiz/verify/" })
	public HashMap<String, String> processAnswer(@RequestBody HashMap<String, String> json) {
		String matchId = json.get("matchId");
		String email = json.get("email");
		String guess = json.get("guess");
		String realAnswer = null;
		try {
			realAnswer = matchService.getAnswer(email, guess, matchId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("answer", realAnswer);
		return map;
	}

	@GetMapping(path = { "/match/{matchId}/{email}/next", "/match/{matchId}/{email}/next/" })
	public QuestionDto getNextQuestion(@PathVariable String matchId, @PathVariable String email) {
		QuestionDto dto = null;
		try {
			Question question = matchService.getNextQuestion(email, matchId);
			if (question != null) {
				dto = QuestionDto.toDto(question);
			} else {
				dto = QuestionDto.empty();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return dto;
	}

	@GetMapping(path = { "/match/postGameStat/{matchId}/{email}", "/match/postGameStat/{matchId}/{email}/" })
	public PostGameStatDto getPostGameStat(@PathVariable String matchId, @PathVariable String email) {
		PostGameStatDto dto = null;
		try {
			dto = matchService.getPostGameStat(email, matchId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return dto;
	}

}
