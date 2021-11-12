package ECSE428Project.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class)
public class Account {

	// ------------------------
	// ATTRIBUTES
	// ------------------------

	@Id
	private String email;

	private String name;
	private String password;
	private boolean isVerified;
	private boolean isLoggedIn;
	private int score;
	private int level;

	// ------------------------
	// ASSOCIATIONS
	// ------------------------

	@ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
	private Set<Match> matches;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Account() {
		name = null;
		email = null;
		password = null;
		isVerified = false;
		isLoggedIn = false;
		score = 0;
		level = 0;
		matches = new HashSet<Match>();
	}

	public Account(String aName, String aEmail, String aPassword, boolean verified, boolean loggedIn, int aScore,
			int aLevel) {
		name = aName;
		email = aEmail;
		password = aPassword;
		isVerified = verified;
		isLoggedIn = loggedIn;
		score = aScore;
		level = aLevel;
		matches = new HashSet<Match>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLoggedIn(boolean loggedIn) {
		isLoggedIn = loggedIn;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	public void addMatches(HashSet<Match> matches) {
		for (Match match : matches) {
			addMatch(match);
		}
	}

	public void addMatch(Match match) {
		this.matches.add(match);
		if(match.getAccounts().isEmpty() || !match.getAccounts().contains(this)){
			match.addAccount(this);
		}
	}

	public void removeMatch(Match match) {
		this.matches.remove(match);
		if(match.getAccounts().contains(this)){
			match.removeAccount(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Account && ((Account) o).email.equals(this.email)) {
			return true;
		}
		return false;
	}
}
