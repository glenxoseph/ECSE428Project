package ECSE428Project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
        hint = null;
        player = null;
    }

    public Question(String aId, String aAskedQuestion, String aHint, Player aPlayer) {
        id = aId;
        askedQuestion = aAskedQuestion;
        hint = aHint;
        setPlayer(aPlayer);
    }


    //------------------------
    // INTERFACE
    //------------------------


    public void setId(String id) { this.id = id; }

    public void setAskedQuestion(String askedQuestion) { this.askedQuestion = askedQuestion; }

    public void setHint(String hint) { this.hint = hint; }

    public void setPlayer(Player player) { this.player = player; }

    public String getId() { return id; }

    public String getAskedQuestion() { return askedQuestion; }

    public String getHint() { return hint; }

    public Player getPlayer() { return player; }
}
