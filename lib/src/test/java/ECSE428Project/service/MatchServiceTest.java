package ECSE428Project.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.MatchRepository;
import ECSE428Project.dao.QuizRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.Match;
import ECSE428Project.model.Quiz;
import ECSE428Project.model.TestUtilities;

@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MatchServiceTest {
	
	@Autowired
	private MatchService matchService;
	
	@MockBean
    private AccountRepository accountRepository;
	
	@MockBean
	private MatchRepository matchRepository;
	
	@MockBean
	private QuizRepository quizRepository;
	
	@Test
	public void getAccountMatch() {
		Account testAccount = TestUtilities.createAccount(1);
		Set<Match> matchs = new HashSet<>();
		Match match1 = new Match();
		match1.setMatchId("match1");
		matchs.add(match1);
        when(accountRepository.findById(testAccount.getEmail())).thenReturn(Optional.of(testAccount));
        when(matchRepository.findByAccounts_Email(testAccount.getEmail())).thenReturn(matchs);
        Set<Match> actual = matchService.getMatchOfAccount(testAccount.getEmail());
        assertArrayEquals(actual.toArray(), matchs.toArray());
	}
	
	@Test
	public void getAccountMatchWrongEmail() {
		Account testAccount = TestUtilities.createAccount(1);
        when(accountRepository.findById(testAccount.getEmail())).thenReturn(Optional.empty());
        assertNull(matchService.getMatchOfAccount(testAccount.getEmail()));
	}
	
	@Test
	public void getAllMatches() {
		int i = 0;
		Match m1 = createMatch(i++);
		Match m2 = createMatch(i++);
		Match m3 = createMatch(i++);
		Match m4 = createMatch(i++);
		Match[] allMatch = {m1,m2,m3,m4};
		when(matchRepository.findAll()).thenReturn(List.of(allMatch));
		Set<Match> matches = matchService.getAllMatch();
		assertTrue(matches.containsAll(List.of(allMatch)));
		assertEquals(allMatch.length, matches.size());
	}
	
	@Test
	public void createMatch() {
		int i = 0;
		Account account1 = TestUtilities.createAccount(i++);
		Account account2 = TestUtilities.createAccount(i++);
		Account account3 = TestUtilities.createAccount(i++);
        when(accountRepository.findById(account1.getEmail())).thenReturn(Optional.of(account1));
        when(accountRepository.findById(account2.getEmail())).thenReturn(Optional.of(account2));
        when(accountRepository.findById(account3.getEmail())).thenReturn(Optional.of(account3));
        prepareQuiz();
        List<String> emails = new ArrayList<>();
        emails.add(account1.getEmail());
        emails.add(account2.getEmail());
        emails.add(account3.getEmail());
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        
        when(matchRepository.save(any(Match.class))).thenAnswer(returnParameterAsAnswer);
        Match match = matchService.createMatch(emails);
        assertTrue(accounts.containsAll(match.getAccounts()));
        assertEquals(accounts.size(), match.getAccounts().size());
	}
	
	@Test
	public void createMatchWrongEmail() {
		int i = 0;
		Account account1 = TestUtilities.createAccount(i++);
		Account account2 = TestUtilities.createAccount(i++);
		Account account3 = TestUtilities.createAccount(i++);
        when(accountRepository.findById(account1.getEmail())).thenReturn(Optional.of(account1));
        when(accountRepository.findById(account2.getEmail())).thenReturn(Optional.of(account2));
        List<String> emails = new ArrayList<>();
        emails.add(account1.getEmail());
        emails.add(account2.getEmail());
        emails.add(account3.getEmail());
        prepareQuiz();
        
        assertThrows(IllegalArgumentException.class, () -> matchService.createMatch(emails), "No account with email " + account3.getEmail());
        verify(matchRepository, never()).save(any(Match.class));
	}
	
	private Match createMatch(int id) {
		Match match = new Match();
		match.setMatchId("" + id);
		return match;
	}
	
	private void prepareQuiz() {
		List<Quiz> quizzes = new ArrayList<>();
        Quiz quiz = new Quiz();
        quizzes.add(quiz);
        when(quizRepository.findAll()).thenReturn(quizzes);
	}
}
