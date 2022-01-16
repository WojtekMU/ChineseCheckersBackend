package TPLab4.ChineseCheckersBackend.Move;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;

/**
 * Class representing moves.
 */
@Entity
public class Move 
{
	/**
	 * Move id.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	private Long id;

	/**
	 * Player who performed the move.
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User player;

	/**
	 * History where the move is.
	 */
	@ManyToOne
    @JoinColumn(name = "history_id")
	@JsonIgnore
    private History history;

	/**
	 * Move first tile.
	 */
	@ManyToOne
    @JoinColumn(name = "first_tile_id")
    private Tile firstTile;

	/**
	 * Move second tile.
	 */
	@ManyToOne
    @JoinColumn(name = "second_tile_id")
    private Tile secondTile;

	/**
	 * Move id getter.
	 * @return Move id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * Move id setter.
	 * @param id Move id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Move player getter.
	 * @return Move player
	 */
	public User getPlayer()
	{
		return player;
	}

	/**
	 * Move player setter.
	 * @param player Player
	 */
	public void setPlayer(User player) 
	{
		this.player = player;
	}

	/**
	 * Move history getter.
	 * @return Move history
	 */
	public History getHistory()
	{
		return history;
	}

	/**
	 * Move history setter.
	 * @param history History
	 */
	public void setHistory(History history)
	{
		this.history = history;
	}

	/**
	 * Move first tile getter.
	 * @return Move first tile
	 */
	public Tile getFirstTile() 
	{
		return firstTile;
	}

	/**
	 * Move first tile setter.
	 * @param firstTile Tile
	 */
	public void setFirstTile(Tile firstTile) 
	{
		this.firstTile = firstTile;
	}

	/**
	 * Move second tile getter.
	 * @return Move second tile
	 */
	public Tile getSecondTile()
	{
		return secondTile;
	}

	/**
	 * Move second tile setter.
	 * @param secondTile Tile
	 */
	public void setSecondTile(Tile secondTile)
	{
		this.secondTile = secondTile;
	}
}
