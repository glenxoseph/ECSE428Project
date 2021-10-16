package ECSE428Project.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "match")
public class Match {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    private String matchId;

    private String winnerId;
    private String winnerName;
    private GameMode gameMode;
    private Options options;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne(optional = false)
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
        account = null;
        player = null;
        rounds = new ArrayList<>();
    }

    public Match(String aMatchId, String aWinnerId, String aWinnerName, GameMode aGameMode, Options someOptions, Account anAccount, Player aPlayer) {
        matchId = aMatchId;
        winnerId = aWinnerId;
        winnerName = aWinnerName;
        gameMode = aGameMode;
        options = someOptions;
        setAccount(anAccount);
        setPlayer(aPlayer);
        rounds = new ArrayList<>();
    }


    //------------------------
    // INTERFACE
    //------------------------


    public String getMatchId() { return matchId; }

    public String getWinnerId() { return winnerId; }

    public String getWinnerName() { return winnerName; }

    public GameMode getGameMode() { return gameMode; }

    public Options getOptions() { return options; }

    public Account getAccount() { return account; }

    public Player getPlayer() { return player; }

    public List<Round> getRounds() { return rounds; }

    public void setMatchId(String matchId) { this.matchId = matchId; }

    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }

    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public void setGameMode(GameMode gameMode) { this.gameMode = gameMode; }

    public void setOptions(Options options) { this.options = options; }

    public void setAccount(Account account) { this.account = account; }

    public void setPlayer(Player player) { this.player = player; }

    public void setRounds(List<Round> rounds) { this.rounds = rounds; }
}
