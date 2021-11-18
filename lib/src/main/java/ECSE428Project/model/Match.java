package ECSE428Project.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "match")
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
    
    @ManyToOne
    private Quiz quiz;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @Cascade(CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "match_account", joinColumns = { @JoinColumn(name = "match_id") }, inverseJoinColumns = {
			@JoinColumn(name = "account_id") })
    private Set<Account> accounts;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Player> players;

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
        players = new HashSet<Player>();
    }

    public Match(String aMatchId, String aWinnerId, String aWinnerName, GameMode aGameMode, Options someOptions, Player aPlayer) {
        matchId = aMatchId;
        winnerId = aWinnerId;
        winnerName = aWinnerName;
        gameMode = aGameMode;
        options = someOptions;
        addPlayer(aPlayer);
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

    public Set<Player> getPlayers() { return players; }

    public void setMatchId(String matchId) { this.matchId = matchId; }

    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }

    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public void setGameMode(GameMode gameMode) { this.gameMode = gameMode; }

    public void setOptions(Options options) { this.options = options; }

    public void addPlayer(Player player) { this.players.add(player); }
    
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
	
	public Quiz getQuiz() {
		return quiz;
	}
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Match && this.matchId != null && this.matchId.equals(((Match) o).matchId);
	}
}
