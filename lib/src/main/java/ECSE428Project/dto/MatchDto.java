package ECSE428Project.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToOne;
import ECSE428Project.model.GameMode;
import ECSE428Project.model.Match;
import ECSE428Project.model.Options;

public class MatchDto {
	
	private String matchId;
    private String winnerId;
    private String winnerName;
    private String gameMode;
    private String options;
    
    @ManyToOne
    private QuizDto quiz;


    //------------------------
    // ASSOCIATIONS
    //------------------------

    private Set<AccountDto> accounts;
    
    public static MatchDto toDto(Match match) {
    	MatchDto dto = new MatchDto();
    	dto.matchId = match.getMatchId();
    	dto.winnerId = match.getWinnerId();
    	dto.winnerName = match.getWinnerName();
    	dto.gameMode = match.getGameMode() == null ? null :match.getGameMode().toString();
    	dto.options = match.getOptions() == null ? null :match.getOptions().toString();
    	dto.quiz = QuizDto.toDto(match.getQuiz());
    	dto.accounts = new HashSet<AccountDto>();
    	match.getAccounts().iterator().forEachRemaining(account ->
    	dto.accounts.add(AccountDto.toDto(account)));
    	return dto;
    }

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode.toString();
	}
	
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options.toString();
	}
	
	public void setOptions(String options) {
		this.options = options;
	}

	public QuizDto getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizDto quiz) {
		this.quiz = quiz;
	}

	public Set<AccountDto> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<AccountDto> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccountDto(AccountDto dto) {
		accounts.add(dto);
	}

}
