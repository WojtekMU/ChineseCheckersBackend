package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Game repository class
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long>
{
    /**
     * Method for getting all games by game status.
     * @param gameStatus Game status
     * @return Game set
     */
    Set<Game> findAllByGameStatus(GameStatus gameStatus);
}
