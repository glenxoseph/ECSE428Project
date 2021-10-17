package ECSE428Project.dto;

public class AccountDto {
    private String email;
    private String name;
    private String password;
    private boolean isVerified;
    private boolean isLoggedIn;
    private int score;
    private int level;

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public boolean isVerified() { return isVerified; }

    public boolean isLoggedIn() { return isLoggedIn; }

    public int getScore() { return score; }

    public int getLevel() { return level; }

    public void setEmail(String email) { this.email = email; }

    public void setName(String name) { this.name = name; }

    public void setPassword(String password) { this.password = password; }

    public void setVerified(boolean verified) { isVerified = verified; }

    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }

    public void setScore(int score) { this.score = score; }

    public void setLevel(int level) { this.level = level; }
}
