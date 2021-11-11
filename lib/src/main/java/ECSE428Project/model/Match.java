package ECSE428Project.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "match", fetch = FetchType.LAZY)
    private Set<Account> account = new HashSet<>();

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

    public Match(String aMatchId, String aWinnerId, String aWinnerName, GameMode aGameMode, Options someOptions, Player aPlayer) {
        matchId = aMatchId;
        winnerId = aWinnerId;
        winnerName = aWinnerName;
        gameMode = aGameMode;
        options = someOptions;
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

    public Set<Account> getAccounts() { return account; }

    public Player getPlayer() { return player; }

    public List<Round> getRounds() { return rounds; }

    public void setMatchId(String matchId) { this.matchId = matchId; }

    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }

    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public void setGameMode(GameMode gameMode) { this.gameMode = gameMode; }

    public void setOptions(Options options) { this.options = options; }

    public void setAccount(HashSet<Account> accounts) { this.account = accounts; }

    public void setPlayer(Player player) { this.player = player; }

    public void setRounds(List<Round> rounds) { this.rounds = rounds; }
}
