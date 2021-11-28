package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.LeaderboardRepository;
import ECSE428Project.dto.LeaderboardDto;
import ECSE428Project.model.Account;
import ECSE428Project.model.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Leaderboard createLeaderboardEntry(LeaderboardDto leaderboardDto) {

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setQuizName(leaderboardDto.getQuizName());
        leaderboard.setQuizScore(leaderboardDto.getQuizScore());
        leaderboard.setAccountEmail(leaderboardDto.getAccountEmail());
        leaderboard.setId(leaderboardDto.getId());

        leaderboard = leaderboardRepository.save(leaderboard);

        return leaderboard;
    }

    @Transactional
    public List<Leaderboard> getLeaderboardEntries(String accountEmail) {
        if (accountEmail == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account cannot be null.");
        }

        Optional<Account> optionalAccount = accountRepository.findById(accountEmail);

        if (!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account associated to this email.");
        } else {
            return leaderboardRepository.findLeaderboardByAccountEmail(accountEmail);
        }

    }
}
