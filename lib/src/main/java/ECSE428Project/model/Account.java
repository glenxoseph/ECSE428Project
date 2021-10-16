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
    private String email;

    private String name;
    private String password;
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
        name = null;
        email = null;
        password = null;
        isVerified = false;
        isLoggedIn = false;
        score = 0;
        level = 0;
        accountMatches = new ArrayList<>();
    }

    public Account(String aName, String aEmail, String aPassword, boolean verified, boolean loggedIn, int aScore, int aLevel) {
        name = aName;
        email = aEmail;
        password = aPassword;
        isVerified = verified;
        isLoggedIn = loggedIn;
        score = aScore;
        level = aLevel;
        accountMatches = new ArrayList<>();
    }


    //------------------------
    // INTERFACE
    //------------------------

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public boolean isLoggedIn() { return isLoggedIn; }

    public boolean isVerified() { return isVerified; }

    public int getScore() { return score; }

    public int getLevel() { return level; }

    public List<Match> getAccountMatches() { return accountMatches; }


    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }

    public void setVerified(boolean verified) { isVerified = verified; }

    public void setScore(int score) { this.score = score; }

    public void setLevel(int level) { this.level = level; }

    public void setAccountMatches(List<Match> accountMatches) { this.accountMatches = accountMatches; }
}

