package TPLab4.ChineseCheckersBackend.History;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long>
{
	Optional<History> findByGameId(Long gameId);
	List<History> findByLeaderboard_Id(Long userId);
}
