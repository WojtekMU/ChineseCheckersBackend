package TPLab4.ChineseCheckersBackend.History;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * History repository class
 */
public interface HistoryRepository extends JpaRepository<History, Long>
{
	/**
	 * Method for finding history by game id.
	 * @param gameId Game id
	 * @return Optional of history
	 */
	Optional<History> findByGameId(Long gameId);

	/**
	 * Method for finding the leaderboard
	 * @param userId User id
	 * @return Leaderboard
	 */
	List<History> findByLeaderboard_Id(Long userId);
}
