package TPLab4.ChineseCheckersBackend.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Game repository class
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    /**
     * Method for getting all games by game status.
     *
     * @param gameStatus Game status
     * @return Game set
     */
    Set<Game> findAllByGameStatus(GameStatus gameStatus);
}
