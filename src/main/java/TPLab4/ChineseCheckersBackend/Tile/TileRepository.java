package TPLab4.ChineseCheckersBackend.Tile;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TileRepository extends JpaRepository<Tile, Long>
{
	List<Tile> findByGameId(Long gameId);
	Optional<Tile> findByXAndYAndGameId(Long x, Long y, Long gameId);
	
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.y < 5", nativeQuery = true)
	List<Tile> getFirstCorner(Long gameId);	
	
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x > 13", nativeQuery = true)
	List<Tile> getSecondCorner(Long gameId);	
	
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x + t.y > 22", nativeQuery = true)
	List<Tile> getThirdCorner(Long gameId);	
	
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.y > 13", nativeQuery = true)
	List<Tile> getFourthCorner(Long gameId);	
	
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x < 5", nativeQuery = true)
	List<Tile> getFifthCorner(Long gameId);	
	
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x + t.y < 14", nativeQuery = true)
	List<Tile> getSixthCorner(Long gameId);	
}
