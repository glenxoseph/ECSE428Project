package ECSE428Project.controller;

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
import ECSE428Project.model.GameMode;
import ECSE428Project.model.Match;
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
		matches.iterator().forEachRemaining(match ->dtos.add(MatchDto.toDto(match)));

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
		matches.iterator().forEachRemaining(match ->dtos.add(MatchDto.toDto(match)));
		return dtos;
	}

	@PostMapping(path = { "/match/create", "/match/create/" })
	public MatchDto createMatch(@RequestBody List<String> emails,
			@RequestParam(value = "rounds", defaultValue = "10") Integer rounds,
			@RequestParam(value = "gamemode", defaultValue = "SOLO") GameMode mode,
			@RequestParam(value = "quizName", required = false) String quizName) throws ResponseStatusException {
		Match match = null;
		try {
			match = matchService.createMatch(emails, rounds, mode, quizName);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		return MatchDto.toDto(match);
	}
}
