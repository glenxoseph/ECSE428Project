package ECSE428Project.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "match")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class)
public class Match {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String matchId;

    private String winnerId;
    private String winnerName;
    private GameMode gameMode;
    private Options options;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @Cascade(CascadeType.ALL)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "match_account", joinColumns = { @JoinColumn(name = "match_id") }, inverseJoinColumns = {
			@JoinColumn(name = "account_id") })
    private Set<Account> accounts;

    @ManyToOne(optional = true)
    private Player player;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Round> rounds;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Match() {
        matchId = null;
        winnerId = null;
        winnerName = null;
        gameMode = null;
        options = null;
        accounts = new HashSet<Account>();
        player = null;
        rounds = new ArrayList<>();
    }

    public Match(String aMatchId, String aWinnerId, String aWinnerName, GameMode aGameMode, Options someOptions, Player aPlayer) {
        matchId = aMatchId;
        winnerId = aWinnerId;
        winnerName = aWinnerName;
        gameMode = aGameMode;
        options = someOptions;
        setPlayer(aPlayer);
        rounds = new ArrayList<>();
        accounts = new HashSet<Account>();
    }


    //------------------------
    // INTERFACE
    //------------------------


    public String getMatchId() { return matchId; }

    public String getWinnerId() { return winnerId; }

    public String getWinnerName() { return winnerName; }

    public GameMode getGameMode() { return gameMode; }

    public Options getOptions() { return options; }

    public Set<Account> getAccounts() { return accounts; }

    public Player getPlayer() { return player; }

    public List<Round> getRounds() { return rounds; }

    public void setMatchId(String matchId) { this.matchId = matchId; }

    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }

    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public void setGameMode(GameMode gameMode) { this.gameMode = gameMode; }

    public void setOptions(Options options) { this.options = options; }

    public void setPlayer(Player player) { this.player = player; }

    public void setRounds(List<Round> rounds) { this.rounds = rounds; }
    
    public void setAccounts(Set<Account> accounts) {
    	this.accounts = accounts;
    }
    
    public void addAccounts(HashSet<Account> accounts) {
		for (Account account: accounts) {
			addAccount(account);
		}
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
		if(account.getMatches().isEmpty() || !account.getMatches().contains(this)) {
			account.addMatch(this);
		}
	}

	public void removeAccount(Account account) {
		this.accounts.remove(account);
		if(account.getMatches().contains(this)) {
			account.removeMatch(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Match && ((Match) o).matchId.equals(this.matchId)) {
			return true;
		}
		return false;
	}
}
