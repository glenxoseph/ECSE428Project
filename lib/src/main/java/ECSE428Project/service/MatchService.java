package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.MatchRepository;
import ECSE428Project.dao.PlayerRepository;
import ECSE428Project.dto.PostGameStatDto;
import ECSE428Project.model.Account;
import ECSE428Project.model.GameMode;
import ECSE428Project.model.Match;
import ECSE428Project.model.Player;
import ECSE428Project.model.Question;
import ECSE428Project.model.Quiz;
import java.util.ArrayList;
import java.util.HashMap;
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

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PlayerRepository playerRepository;

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
	public Match createMatch(List<String> emails, GameMode mode, String quizName) {
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
		addPlayersToMatch(match);
		match = matchRepository.save(match);
		return match;
	}
	
	private void addPlayersToMatch(Match match) {
		match.getAccounts().forEach(account -> match.addPlayer(playerFromAccount(account)));
	}
	
	private Player playerFromAccount(Account account) {
		Player player = new Player();
		player.setAccountEmail(account.getEmail());
		return player;
	}

	@Transactional
	public Match createMatch(List<String> emails) {
		return createMatch(emails, GameMode.SOLO, null);
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
	
	@Transactional
	private String processAnswer(Player player, String answer, Match match) {
		int curIndex = player.getGivenAnswers().size();
		player.addGivenAnswer(answer);
		if(match.getQuiz().getQuestions().get(curIndex).getAnswer().equals(answer)) {
			player.setNumberOfCorrectAnswers(player.getNumberOfCorrectAnswers() + 1);
		}
		match = matchRepository.save(match);
		return match.getQuiz().getQuestions().get(curIndex).getAnswer();
	}
	
	private Question getNextQuestion(Player player, Match match) {
		int curIndex = player.getGivenAnswers().size();
		if(curIndex < match.getQuiz().getQuestions().size()) {
			return match.getQuiz().getQuestions().get(curIndex);
		}
		return null;
	}
	
	@Transactional
	public Account updateStats(Player player, Match match, PostGameStatDto stat) {
		int levelGain = (match.getQuiz().getQuestions().size() / 2) + player.getNumberOfCorrectAnswers();
		int pointsGained = player.getNumberOfCorrectAnswers() * 20;
		Account account = accountRepository.findById(player.getAccountEmail()).orElse(null);
		if(account != null) {
			account.setScore(account.getScore() + pointsGained);
			account.setLevel(account.getLevel() + levelGain);
		}
		if(match.getGameMode().equals(GameMode.SOLO)) {
			double nbQuestion = match.getQuiz().getQuestions().size();
			double nbCorrect = player.getNumberOfCorrectAnswers();
			if((nbCorrect / nbQuestion) >= 0.8) {
				match.setWinnerId(account.getEmail());
				match.setWinnerName(account.getName());
				match = matchRepository.save(match);
			}
		}
		stat.setNumberOfCorrectAnswers(player.getNumberOfCorrectAnswers());
		stat.setPointsGained(pointsGained);
		stat.setLevelGained(levelGain);
		return accountRepository.save(account);
	}
	
	public PostGameStatDto getPostGameStat(Player player, Match match) {
		PostGameStatDto stat = new PostGameStatDto();
		updateStats(player, match, stat);
		return stat;
	}
	
	public PostGameStatDto getPostGameStat(String email, String matchId) {
		HashMap<String, Object> map = getPlayerAndMatch(email, matchId);	
		return getPostGameStat((Player)map.get("player"), (Match)map.get("match"));
	}
	
	@Transactional
	public Question getNextQuestion(String email, String matchId) {
		HashMap<String, Object> map = getPlayerAndMatch(email, matchId);	
		return getNextQuestion((Player)map.get("player"), (Match)map.get("match"));
	}
	
	@Transactional
	public String getAnswer(String email, String answer, String matchId) throws IllegalArgumentException{
		HashMap<String, Object> map = getPlayerAndMatch(email, matchId);	
		return processAnswer((Player)map.get("player"), answer, (Match)map.get("match"));
	}
	
	@Transactional
	private HashMap<String, Object> getPlayerAndMatch(String email, String matchId){
		HashMap<String, Object> map = new HashMap<>();
		Player player = playerRepository.findByAccountEmail(email);
		if(player == null) {
			throw new IllegalArgumentException("No player with associated email " + email + " could be found");
		}
		Match currentMatch = matchRepository.findById(matchId).orElse(null);
		if(currentMatch == null) {
			throw new IllegalArgumentException("No match with id " + matchId + " could be found");
		}
		map.put("player", player);
		map.put("match", currentMatch);
		return map;
	}
		
}
