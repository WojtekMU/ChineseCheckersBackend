package TPLab4.ChineseCheckersBackend.Tile;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Move.Move;

/**
 * Class representing game tiles.
 */
@Entity
public class Tile
{
	/**
	 * Tile id.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

	/**
	 * Tile x coordinate.
	 */
	@Column(name = "x", nullable = false)
	private Long x;

	/**
	 * Tile y coordinate.
	 */
	@Column(name = "y", nullable = false)
	private Long y;

	/**
	 * Tile color.
	 */
	@Enumerated(EnumType.STRING)
	private TileColor color;

	/**
	 * Game to which the tile belongs.
	 */
	@ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

	/**
	 * Game of which the tile is the chosen tile.
	 */
    @OneToOne(mappedBy = "chosenTile")
    @JsonIgnore
    private Game chosenTileGame;

	/**
	 * Empty tile constructor.
	 */
	public Tile() {}

	/**
	 * Default tile constructor.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param color Color
	 * @param game Game
	 */
    public Tile(Long x, Long y, String color, Game game)
	{
		this.x = x;
		this.y = y;
		this.color = TileColor.WHITE;
		this.game = game;
	}

	/**
	 * Tile id getter.
	 * @return Tile id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * Tile x getter.
	 * @return Tile x
	 */
	public Long getX() 
	{
		return x;
	}

	/**
	 * Tile x setter.
	 * @param x Long value
	 */
	public void setX(Long x) 
	{
		this.x = x;
	}

	/**
	 * Tile y getter.
	 * @return Tile y
	 */
	public Long getY() 
	{
		return y;
	}

	/**
	 * Tile y setter.
	 * @param y Long value
	 */
	public void setY(Long y) 
	{
		this.y = y;
	}

	/**
	 * Tile color getter.
	 * @return Tile color
	 */
	public TileColor getColor() 
	{
		return color;
	}

	/**
	 * Tile color setter.
	 * @param color TileColor enum
	 */
	public void setColor(TileColor color) 
	{
		this.color = color;
	}

	/**
	 * Tile game getter
	 * @return Game
	 */
	public Game getGame() 
	{
		return game;
	}

	/**
	 * Tile game setter.
	 * @param game Game
	 */
	public void setGame(Game game) 
	{
		this.game = game;
	}

	/**
	 * Tile id setter
	 * @param id Long value
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Chosen tile game getter.
	 * @return Chosen tile game
	 */
	public Game getChosenTileGame() {
		return chosenTileGame;
	}

	/**
	 * Chosen tile game setter
	 * @param chosenTileGame Game
	 */
	public void setChosenTileGame(Game chosenTileGame) {
		this.chosenTileGame = chosenTileGame;
	}
}
