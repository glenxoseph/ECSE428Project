package ECSE428Project.dao;

import ECSE428Project.model.Leaderboard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends CrudRepository<Leaderboard, String> {
    List<Leaderboard> findLeaderboardByAccountEmail(String accountEmail);
}
