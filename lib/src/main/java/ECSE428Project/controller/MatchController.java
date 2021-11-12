package ECSE428Project.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ECSE428Project.model.Match;
import ECSE428Project.service.MatchService;

@CrossOrigin(origins = "*")
@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;

	@GetMapping(path = { "/match/history", "/match/history/" })
	public Set<Match> getAllMatches() throws ResponseStatusException {
		return matchService.getAllMatch();
	}

	@GetMapping(path = { "/match/history/{email}", "/match/history/{email}/" })
	public Set<Match> getAccountMatches(@PathVariable String email) throws ResponseStatusException {
		Set<Match> matches = matchService.getMatchOfAccount(email);
		if (matches == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"No account with the corresponding email can be found.");
		}

		return matches;
	}

	@PostMapping(path = { "/match/create", "/match/create/" })
	public Match createMatch(@RequestBody List<String> emails) throws ResponseStatusException {
		Match match = null;
		try {
			match = matchService.createMatch(emails);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return match;
	}
}
