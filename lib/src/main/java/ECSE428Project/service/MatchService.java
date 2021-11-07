package ECSE428Project.service;

import ECSE428Project.dao.MatchRepository;
import ECSE428Project.model.AdminConfig;
import ECSE428Project.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

	@Transactional
	public List<Match> getAllMatches() {
		return (List<Match>) matchRepository.findAll();
	}
}