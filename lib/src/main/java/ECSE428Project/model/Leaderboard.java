package ECSE428Project.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "leaderboard")
public class Leaderboard {

    // ------------------------
    // ATTRIBUTES
    // ------------------------

    private String quizName;
    private String quizScore;
    private String accountEmail;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    // ------------------------
    // CONSTRUCTOR
    // ------------------------

    public Leaderboard() {}

    public Leaderboard(String quizName, String quizScore, String accountEmail) {
        this.quizName = quizName;
        this.quizScore = quizScore;
        this.accountEmail = accountEmail;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }

    public String getQuizName() { return quizName; }

    public String getQuizScore() { return quizScore; }

    public void setQuizName(String quizName) { this.quizName = quizName; }

    public void setQuizScore(String quizScore) { this.quizScore = quizScore; }

    public String getAccountEmail() { return accountEmail; }

    public void setAccountEmail(String accountEmail) { this.accountEmail = accountEmail; }
}
