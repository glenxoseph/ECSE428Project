package ECSE428Project.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "round")
public class Round {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    private String roundId;

    private RoundMode roundMode;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL})
    private List<Match> matches;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Round() {
        roundId = null;
        roundMode = null;
        matches = new ArrayList<>();
    }

    public Round(String aId, RoundMode aRoundMode) {
        roundId = aId;
        roundMode = aRoundMode;
        matches = new ArrayList<>();
    }


    //------------------------
    // INTERFACE
    //------------------------

    public String getRoundId() { return roundId; }

    public RoundMode getRoundMode() { return roundMode; }

    public List<Match> getMatches() { return matches; }

    public void setRoundId(String roundId) { this.roundId = roundId; }

    public void setRoundMode(RoundMode roundMode) { this.roundMode = roundMode; }

    public void setMatches(List<Match> matches) { this.matches = matches; }
}
