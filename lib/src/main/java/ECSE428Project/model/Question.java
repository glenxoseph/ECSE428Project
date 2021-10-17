package ECSE428Project.model;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "question")
public class Question {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    private String id;

    private String askedQuestion;
    private String hint;

    @ElementCollection
    private List<String> acceptedAnswers;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @ManyToOne(optional = false)
    private Player player;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Question() {
        id = null;
        askedQuestion = null;
        acceptedAnswers = new ArrayList<>();
        hint = null;
        player = null;
    }

    public Question(String aId, String anAskedQuestion, String aHint, List<String> someAcceptedAnswers, Player aPlayer) {
        id = aId;
        askedQuestion = anAskedQuestion;
        hint = aHint;
        acceptedAnswers = someAcceptedAnswers;
        setPlayer(aPlayer);
    }


    //------------------------
    // INTERFACE
    //------------------------


    public void setId(String id) { this.id = id; }

    public void setAskedQuestion(String askedQuestion) { this.askedQuestion = askedQuestion; }

    public void setHint(String hint) { this.hint = hint; }

    public void setAcceptedAnswers(List<String> acceptedAnswers) { this.acceptedAnswers = acceptedAnswers; }

    public void setPlayer(Player player) { this.player = player; }

    public String getId() { return id; }

    public String getAskedQuestion() { return askedQuestion; }

    public String getHint() { return hint; }

    public List<String> getAcceptedAnswers() { return acceptedAnswers; }

    public Player getPlayer() { return player; }
}
