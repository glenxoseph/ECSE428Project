package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.MatchRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.GameMode;
import ECSE428Project.model.Match;
import ECSE428Project.model.Quiz;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

	private static final int DEFAULT_ROUND_NB = 10;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private QuizService quizService;

	@Transactional
	public Set<Match> getMatchOfAccount(String email) {
		Optional<Account> optAccount = accountRepository.findById(email);
		if (optAccount.isPresent()) {
			return matchRepository.findByAccounts_Email(email);
		} else {
			return null;
		}
	}

	@Transactional
	public Set<Match> getAllMatch() {
		Set<Match> matches = new HashSet<>();
		Iterator<Match> ite = matchRepository.findAll().iterator();
		while (ite.hasNext()) {
			matches.add(ite.next());
		}
		return matches;
	}

	@Transactional
	public Match createMatch(List<String> emails, Integer rounds, GameMode mode, String quizName) {
		Match match = new Match();
		for (String email : emails) {
			Optional<Account> optAccount = accountRepository.findById(email);
			if (!optAccount.isPresent()) {
				throw new IllegalArgumentException("No account with email " + email);
			}
			Hibernate.initialize(optAccount.get().getMatches());
			match.addAccount(optAccount.get());
		}
		match.setGameMode(mode);
		addQuizToMatch(match, quizName);
		match = matchRepository.save(match);
		return match;
	}

	@Transactional
	public Match createMatch(List<String> emails) {
		return createMatch(emails, DEFAULT_ROUND_NB, GameMode.SOLO, null);
	}

	@Transactional
	private void addQuizToMatch(Match match, String quizName) {
		Quiz quiz = null;
		if (quizName != null) {
			quiz = quizService.getQuizByName(quizName);
		}
		if (quiz == null) {
			quiz = findRandomQuizForMatch(match);
		}
		match.setQuiz(quiz);
	}

	@Transactional
	private Quiz findRandomQuizForMatch(Match match) throws EntityNotFoundException {
		Quiz chosenQuiz = null;
		int index = 0;
		Set<Account> accounts = match.getAccounts();
		Set<String> playedQuizzesId = new HashSet<>();
		List<Quiz> allQuizzes = quizService.getAllQuizzes();
		if (allQuizzes.size() <= 0) {
			throw new EntityNotFoundException("No quiz could be found. Has one been created yet?");
		}

		for (Account account : accounts) {
			for (Match m1 : account.getMatches()) {
				if (!match.equals(m1)) {
					if (m1.getQuiz() != null) {
						playedQuizzesId.add(m1.getQuiz().getId());
					}
				}
			}
		}
		List<Quiz> notYetPlayed = new ArrayList<>();
		allQuizzes.iterator().forEachRemaining(quiz -> {
			if (!playedQuizzesId.contains(quiz.getId())) {
				notYetPlayed.add(quiz);
			}
		});

		if (notYetPlayed.size() > 0) {
			Random rand = new Random();
			int max = notYetPlayed.size();
			index = rand.nextInt(max);
			chosenQuiz = notYetPlayed.get(index);
		} else {
			Random rand = new Random();
			int max = allQuizzes.size();
			index = rand.nextInt(max);
			chosenQuiz = allQuizzes.get(index);
		}

		return chosenQuiz;
	}
}
