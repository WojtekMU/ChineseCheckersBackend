package TPLab4.ChineseCheckersBackend.Tile;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Tile repository class
 */
@Repository
public interface TileRepository extends JpaRepository<Tile, Long>
{
	/**
	 * Method which filds tile by game id.
	 * @param gameId Game id
	 * @return Optional of tile
	 */
	List<Tile> findByGameId(Long gameId);

	/**
	 * Tile getter by x, y and game id.
	 * @param x X
	 * @param y Y
	 * @param gameId Game id
	 * @return Tile
	 */
	Tile getByXAndYAndGameId(Long x, Long y, Long gameId);

	/**
	 * Getter for the first corner of a game.
	 * @param gameId Game id
	 * @return Tile list
	 */
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.y < 5", nativeQuery = true)
	List<Tile> getFirstCorner(Long gameId);

	/**
	 * Getter for the second corner of a game.
	 * @param gameId Game id
	 * @return Tile list
	 */
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x > 13", nativeQuery = true)
	List<Tile> getSecondCorner(Long gameId);

	/**
	 * Getter for the third corner of a game.
	 * @param gameId Game id
	 * @return Tile list
	 */
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x + t.y > 22", nativeQuery = true)
	List<Tile> getThirdCorner(Long gameId);

	/**
	 * Getter for the fourth corner of a game.
	 * @param gameId Game id
	 * @return Tile list
	 */
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.y > 13", nativeQuery = true)
	List<Tile> getFourthCorner(Long gameId);

	/**
	 * Getter for the fifth corner of a game.
	 * @param gameId Game id
	 * @return Tile list
	 */
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x < 5", nativeQuery = true)
	List<Tile> getFifthCorner(Long gameId);

	/**
	 * Getter for the sixth corner of a game.
	 * @param gameId Game id
	 * @return Tile list
	 */
	@Query(value = "SELECT * FROM Tile t WHERE t.game_id = ?1 AND t.x + t.y < 14", nativeQuery = true)
	List<Tile> getSixthCorner(Long gameId);	
}
