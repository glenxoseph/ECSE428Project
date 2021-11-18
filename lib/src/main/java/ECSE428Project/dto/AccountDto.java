package ECSE428Project.dto;

import ECSE428Project.model.Account;

public class AccountDto {
    private String email;
    private String name;
    private String password;
    private boolean isVerified;
    private boolean isLoggedIn;
    private boolean isAdmin;
    private int score;
    private int level;
    
    public static AccountDto toDto(Account account) {
    	AccountDto dto = new AccountDto();
    	dto.email = account.getEmail();
    	dto.name = account.getName();
    	dto.isLoggedIn = account.isLoggedIn();
    	dto.score = account.getScore();
    	dto.level = account.getLevel();
    	return dto;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public boolean isVerified() { return isVerified; }

    public boolean isLoggedIn() { return isLoggedIn; }
    
    public boolean isAdmin() {return isAdmin;}

    public int getScore() { return score; }

    public int getLevel() { return level; }

    public void setEmail(String email) { this.email = email; }

    public void setName(String name) { this.name = name; }

    public void setPassword(String password) { this.password = password; }

    public void setVerified(boolean verified) { isVerified = verified; }

    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }
    
    public void setIsAdmin(boolean isAdmin) {this.isAdmin = isAdmin;}

    public void setScore(int score) { this.score = score; }

    public void setLevel(int level) { this.level = level; }
}
