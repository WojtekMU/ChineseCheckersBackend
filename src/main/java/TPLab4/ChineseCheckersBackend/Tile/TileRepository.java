package TPLab4.ChineseCheckersBackend.Tile;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TileRepository extends JpaRepository<Tile, Long>
{
	List<Tile> findByGameId(Long gameId);
	Optional<Tile> findByXAndYAndGameId(Long x, Long y, Long gameId);
}
