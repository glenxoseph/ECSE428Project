package ECSE428Project.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String accountEmail;

    private int numberOfCorrectAnswers;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> givenAnswers;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player() {
        id = null;
        numberOfCorrectAnswers = 0;
        givenAnswers = new ArrayList<>();
        //playerMatches = new ArrayList<>();
    }

    public Player(String aId, int aNumberOfCorrectAnswers){
        id = aId;
        numberOfCorrectAnswers = aNumberOfCorrectAnswers;
        givenAnswers = new ArrayList<>();
        //playerMatches = new ArrayList<>();
    }


    //------------------------
    // INTERFACE
    //------------------------

    public void setId(String aId)
    {
        id = aId;
    }

    public void setNumberOfCorrectAnswers(int aNumber) { numberOfCorrectAnswers = aNumber; }

    public String getId() { return id; }

    public int getNumberOfCorrectAnswers() { return numberOfCorrectAnswers; }

    public List<String> getGivenAnswers() {
        List<String> someGivenAnswers = Collections.unmodifiableList(givenAnswers);
        return someGivenAnswers;
    }

    public void setGivenAnswers(List<String> answers) { this.givenAnswers = answers; }
    
    public void addGivenAnswer(String answer) {
    	givenAnswers.add(answer);
    }

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}


    /*public List<Match> getPlayerMatches() {
        List<Match> somePlayerMatches = Collections.unmodifiableList(playerMatches);
        return somePlayerMatches;
    }

    public void setPlayerMatches(List<Match> matches) { this.playerMatches = matches; }*/
}
