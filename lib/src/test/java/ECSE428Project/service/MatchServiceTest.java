package ECSE428Project.service;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ECSE428Project.model.Account;
import ECSE428Project.model.GameMode;
import ECSE428Project.model.Match;
import ECSE428Project.model.Options;
import ECSE428Project.model.Round;
import ECSE428Project.model.TestUtilities;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MatchServiceTest {
	@Autowired
	private MatchService matchService;
	
	@Test
	public void GetAllMatches() {
		String email1 = "admin1@hotmail.com", email2 = "admin2@hotmail.com", email3 = "admin3@hotmail.com";
		Match defaultMatch = new Match();
		Account testAccount = TestUtilities.createAccount(1);
		Account testAccount2 = TestUtilities.createAccount(1);
        String accountName = "accountName1", accountEmail = "accountEmail1@a.ca", accountPassword = "password1";
		defaultMatch.setAccount(new HashSet<Account>());
		defaultMatch.setMatchId("0");
		defaultMatch.setGameMode(GameMode.Solo);
		defaultMatch.setOptions(Options.Easy);
		Round firstRound = new Round();
		Round secondRound = new Round();
		List<Round> roundList = new ArrayList<Round>();
		defaultMatch.setRounds(roundList);
		defaultMatch.setWinnerName("riad");
		defaultMatch.setWinnerId("1");
		Assert.assertEquals(defaultMatch.getWinnerName(), "riad");
		Assert.assertEquals(defaultMatch.getWinnerId(), "1");
		Assert.assertEquals(defaultMatch.getMatchId(), "0");
		Assert.assertEquals(defaultMatch.getGameMode(), GameMode.Solo);
	}
}
