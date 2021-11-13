package ECSE428Project.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class MatchTest {
	
	@Test
	public void testAddMatchToAccount() {
		int i = 0;
		Account a1 = TestUtilities.createAccount(i++);
		Account a2 = TestUtilities.createAccount(i++);
		Account a3 = TestUtilities.createAccount(i++);
		Match m1 = createMatch(i++);
		Match m2 = createMatch(i++);
		Match m3 = createMatch(i++);
		
		a1.addMatch(m1);
		
		a2.addMatch(m1);
		a2.addMatch(m2);
		
		a3.addMatch(m1);
		a3.addMatch(m2);
		a3.addMatch(m3);
		
		Account[]expM1 = {a1,a2,a3};
		Account[]expM2 = {a2,a3};
		Account[]expM3 = {a3};
		
		Match[] expA1 = {m1};
		Match[] expA2 = {m1,m2};
		Match[] expA3 = {m1,m2,m3};
		
		assertTrue(List.of(expM1).containsAll(m1.getAccounts()));
		assertEquals(expM1.length, m1.getAccounts().size());
		
		assertTrue(List.of(expM2).containsAll(m2.getAccounts()));
		assertEquals(expM2.length, m2.getAccounts().size());
		
		assertTrue(List.of(expM3).containsAll(m3.getAccounts()));
		assertEquals(expM3.length, m3.getAccounts().size());
		
		assertTrue(List.of(expA1).containsAll(a1.getMatches()));
		assertEquals(expA1.length, a1.getMatches().size());
		
		assertTrue(List.of(expA2).containsAll(a2.getMatches()));
		assertEquals(expA2.length, a2.getMatches().size());
		
		assertTrue(List.of(expA3).containsAll(a3.getMatches()));
		assertEquals(expA3.length, a3.getMatches().size());
		
	}
	
	@Test
	public void testAddAccountToMatch() {
		int i = 0;
		Account a1 = TestUtilities.createAccount(i++);
		Account a2 = TestUtilities.createAccount(i++);
		Account a3 = TestUtilities.createAccount(i++);
		Match m1 = createMatch(i++);
		Match m2 = createMatch(i++);
		Match m3 = createMatch(i++);
		
		m1.addAccount(a1);
		
		m2.addAccount(a1);
		m2.addAccount(a2);
		
		m3.addAccount(a1);
		m3.addAccount(a2);
		m3.addAccount(a3);
		
		Account[]expM1 = {a1};
		Account[]expM2 = {a1,a2};
		Account[]expM3 = {a1,a2,a3};
		
		Match[] expA1 = {m1,m2,m3};
		Match[] expA2 = {m2,m3};
		Match[] expA3 = {m3};
		
		assertTrue(List.of(expM1).containsAll(m1.getAccounts()));
		assertEquals(expM1.length, m1.getAccounts().size());
		
		assertTrue(List.of(expM2).containsAll(m2.getAccounts()));
		assertEquals(expM2.length, m2.getAccounts().size());
		
		assertTrue(List.of(expM3).containsAll(m3.getAccounts()));
		assertEquals(expM3.length, m3.getAccounts().size());
		
		assertTrue(List.of(expA1).containsAll(a1.getMatches()));
		assertEquals(expA1.length, a1.getMatches().size());
		
		assertTrue(List.of(expA2).containsAll(a2.getMatches()));
		assertEquals(expA2.length, a2.getMatches().size());
		
		assertTrue(List.of(expA3).containsAll(a3.getMatches()));
		assertEquals(expA3.length, a3.getMatches().size());
		
	}
	
	private Match createMatch(int id) {
		Match match = new Match();
		match.setMatchId("" + id);
		return match;
	}
	
}
