package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.MatchRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.Match;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Transactional
    public Set<Match> getMatchOfAccount(String email){
    	Optional<Account> optAccount = accountRepository.findById(email);
    	if(optAccount.isPresent()) {
    		return matchRepository.findByAccounts_Email(email);
    	} else {
    		return null;
    	}
    }
    
    @Transactional
    public Set<Match> getAllMatch(){
    	Set<Match> matches = new HashSet<>();
    	Iterator<Match> ite = matchRepository.findAll().iterator();
    	while(ite.hasNext()) {
    		matches.add(ite.next());
    	}
    	return matches;
    }
    
    @Transactional
    public Match createMatch(List<String> emails) {
    	Match match = new Match();
    	for(String email: emails) {
    		Optional<Account> optAccount = accountRepository.findById(email);
        	if(!optAccount.isPresent()) {
        		throw new IllegalArgumentException("No account with email " + email);
        	}
        	Hibernate.initialize(optAccount.get().getMatches());
        	match.addAccount(optAccount.get());
    	}
    	match = matchRepository.save(match);
    	return match;
    }
}
