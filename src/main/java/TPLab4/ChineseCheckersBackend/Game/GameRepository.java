package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Game repository class
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long>
{
}
