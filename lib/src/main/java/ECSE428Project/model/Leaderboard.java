package ECSE428Project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "leaderboard")
public class Leaderboard {

    @Id
    private String id;

    String Dictionary() {
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
