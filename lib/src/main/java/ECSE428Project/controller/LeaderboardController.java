package ECSE428Project.controller;

import ECSE428Project.dto.LeaderboardDto;
import ECSE428Project.model.Leaderboard;
import ECSE428Project.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class LeaderboardController {

    @Autowired
    LeaderboardService leaderboardService;

    @PostMapping(path = "/leaderboard/createEntry")
    public LeaderboardDto createLeaderboardEntry (@RequestBody LeaderboardDto leaderboardDto) throws ResponseStatusException {
        if(leaderboardDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The quiz name or score cannot be null");
        }

        // Create a leaderboard with the input attributes
        Leaderboard leaderboard = leaderboardService.createLeaderboardEntry(leaderboardDto);

        return LeaderboardDto.toDto(leaderboard);
    }

    @GetMapping(path = "/leaderboard/entries/{email}")
    public List<LeaderboardDto> getLeaderboardEntries (@PathVariable (name="email") String email) throws ResponseStatusException {

        List<Leaderboard> leaderboardEntries;
        List<LeaderboardDto> leaderboardDtos = new ArrayList<>();

        // Get the leaderboard entries and return them as Dtos
        leaderboardEntries = leaderboardService.getLeaderboardEntries(email);

        for (Leaderboard leaderboardEntry : leaderboardEntries) {
            LeaderboardDto leaderboardDto = LeaderboardDto.toDto(leaderboardEntry);
            leaderboardDtos.add(leaderboardDto);
        }

        return leaderboardDtos;
    }

}
