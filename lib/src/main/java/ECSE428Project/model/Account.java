package ECSE428Project.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "account")
public class Account {

    //------------------------
    // ATTRIBUTES
    //------------------------

    @Id
    private String id;

    private String name;
    private String email;
    private boolean isVerified;
    private boolean isLoggedIn;
    private int score;
    private int level;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "account")
    private List<Match> accountMatches;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Account() {
        id = null;
        name = null;
        email = null;
        isVerified = false;
        isLoggedIn = false;
        score = 0;
        level = 0;
        accountMatches = new ArrayList<>();
    }

    public Account(String aId, String aName, String aEmail, boolean verified, boolean loggedIn, int aScore, int aLevel) {
        id = aId;
        name = aName;
        email = aEmail;
        isVerified = verified;
        isLoggedIn = loggedIn;
        score = aScore;
        level = aLevel;
        accountMatches = new ArrayList<>();
    }


    //------------------------
    // INTERFACE
    //------------------------

    public String getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public boolean isLoggedIn() { return isLoggedIn; }

    public boolean isVerified() { return isVerified; }

    public int getScore() { return score; }

    public int getLevel() { return level; }

    public List<Match> getAccountMatches() { return accountMatches; }


    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }

    public void setVerified(boolean verified) { isVerified = verified; }

    public void setScore(int score) { this.score = score; }

    public void setLevel(int level) { this.level = level; }

    public void setAccountMatches(List<Match> accountMatches) { this.accountMatches = accountMatches; }
}

