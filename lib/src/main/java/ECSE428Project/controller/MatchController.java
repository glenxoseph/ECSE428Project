package ECSE428Project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ECSE428Project.model.Match;
import ECSE428Project.service.MatchService;

@CrossOrigin(origins = "*")
@RestController
public class MatchController {
	
	@Autowired
	public MatchService matchService;
	
	@GetMapping(path = { "/history/", "/history" })
	public List<Match> AllMatches() throws ResponseStatusException {
		return matchService.findAll();
	}
}
