package ECSE428Project.service;

import ECSE428Project.dao.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;
}
