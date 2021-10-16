package ECSE428Project.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "answer")
public class Answer {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    private String id;

    private String givenAnswer;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @ManyToOne(optional = false)
    private Player player;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Answer() {
        id = null;
        givenAnswer = null;
        player = null;
    }


    public Answer(String aId, String aGivenAnswer, Player aPlayer) {
        id = aId;
        givenAnswer = aGivenAnswer;
        setPlayer(aPlayer);
    }


    //------------------------
    // INTERFACE
    //------------------------


    public String getId() { return id; }

    public String getGivenAnswer() { return givenAnswer; }

    public Player getPlayer() { return player; }

    public void setId(String id) { this.id = id; }

    public void setGivenAnswer(String givenAnswer) { this.givenAnswer = givenAnswer; }

    public void setPlayer(Player player) { this.player = player; }
}
