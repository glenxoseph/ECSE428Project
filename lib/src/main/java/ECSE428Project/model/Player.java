package ECSE428Project.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    private String id;

    private int numberOfCorrectAnswers;
    private int numberOfWrongAnswers;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "player")
    private List<Answer> givenAnswers;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "player")
    private List<Question> askedQuestions;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "player")
    private List<Match> playerMatches;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player() {
        id = null;
        numberOfCorrectAnswers = 0;
        numberOfWrongAnswers = 0;
        givenAnswers = new ArrayList<>();
        askedQuestions = new ArrayList<>();
        playerMatches = new ArrayList<>();
    }

    public Player(String aId, int aNumberOfCorrectAnswers, int aNumberOfWrongAnswers){
        id = aId;
        numberOfCorrectAnswers = aNumberOfCorrectAnswers;
        numberOfWrongAnswers = aNumberOfWrongAnswers;
        givenAnswers = new ArrayList<>();
        askedQuestions = new ArrayList<>();
        playerMatches = new ArrayList<>();
    }


    //------------------------
    // INTERFACE
    //------------------------

    public void setId(String aId)
    {
        id = aId;
    }

    public void setNumberOfCorrectAnswers(int aNumber) { numberOfCorrectAnswers = aNumber; }

    public void setNumberOfWrongAnswers(int aNumber) { numberOfWrongAnswers = aNumber; }

    public String getId() { return id; }

    public int getNumberOfCorrectAnswers() { return numberOfCorrectAnswers; }

    public int getNumberOfWrongAnswers() { return numberOfWrongAnswers; }


    public List<Answer> getGivenAnswers() {
        List<Answer> someGivenAnswers = Collections.unmodifiableList(givenAnswers);
        return someGivenAnswers;
    }

    public void setGivenAnswers(List<Answer> answers) { this.givenAnswers = answers; }

    public List<Question> getAskedQuestions() {
        List<Question> someAskedQuestions = Collections.unmodifiableList(askedQuestions);
        return someAskedQuestions;
    }

    public void setAskedQuestions(List<Question> questions) { this.askedQuestions = questions; }

    public List<Match> getPlayerMatches() {
        List<Match> somePlayerMatches = Collections.unmodifiableList(playerMatches);
        return somePlayerMatches;
    }

    public void setPlayerMatches(List<Match> matches) { this.playerMatches = matches; }
}
