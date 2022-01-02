package TPLab4.ChineseCheckersBackend.History;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long>
{
	Optional<History> findByGameId(Long gameId);
}
