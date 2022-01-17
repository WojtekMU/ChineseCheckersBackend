package TPLab4.ChineseCheckersBackend.Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;

/**
 * Class representing games
 */
@Entity
public abstract class Game
{
	/**
	 * Game id
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	protected Long id;

	/**
	 * Game participants
	 */
    @ManyToMany
    @JoinTable(
    		  name = "game_user", 
    		  joinColumns = @JoinColumn(name = "game_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "user_id"))
    @OrderColumn
    protected List<User> players = new ArrayList<User>();

	/**
	 * Game status
	 */
	@Enumerated(EnumType.STRING)
    protected GameStatus gameStatus;

	/**
	 * Game board
	 */
	@OneToMany(mappedBy="game", cascade = CascadeType.ALL)
    protected List<Tile> tileList = new ArrayList<Tile>();

	/**
	 * Player turn
	 */
    @Column(name = "player_turn")
    protected Integer playerTurn;

	/**
	 * Game room
	 */
	@OneToOne(mappedBy = "game")
    @JsonIgnore
    protected Room room;

	/**
	 * Game history
	 */
	@OneToOne
	@JoinColumn(name = "history_id")
    protected History history;

	/**
	 * Game chosen tile
	 */
	@OneToOne
    @JoinColumn(name = "tile_id")
    protected Tile chosenTile;

	/**
	 * Is during move
	 */
	@Column(name = "during_move")
    protected Boolean duringMove;

	/**
	 * Game creation date
	 */
	@CreationTimestamp
    @Temporal(TemporalType.TIME)
    @Column(name = "create_date", updatable = false)
    protected Date createDate;

	/**
	 * Game last update
	 */
	@UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    protected Date lastUpdate;

	/**
	 * Game id getter.
	 * @return Game id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * Game id setter.
	 * @param id Game id
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * Game board getter.
	 * @return Game board
	 */
	public List<Tile> getTileList() 
	{
		return tileList;
	}

	/**
	 * Game board setter.
	 * @param tileList Tile list
	 */
	public void setTileList(List<Tile> tileList) 
	{
		this.tileList = tileList;
	}

	/**
	 * Game participants getter.
	 * @return Game participants
	 */
	public List<User> getPlayers() 
	{
		return players;
	}

	/**
	 * Gam participants setter.
	 * @param players User list
	 */
	public void setPlayers(List<User> players)
	{
		this.players = players;
	}

	/**
	 * Player turn getter.
	 * @return Player turn
	 */
	public Integer getPlayerTurn() 
	{
		return playerTurn;
	}

	/**
	 * Player turn setter.
	 * @param playerTurn Player turn
	 */
	public void setPlayerTurn(Integer playerTurn) 
	{
		this.playerTurn = playerTurn;
	}

	/**
	 * Game room getter.
	 * @return Game room
	 */
	public Room getRoom() 
	{
		return room;
	}

	/**
	 * Game room setter.
	 * @param room Room
	 */
	public void setRoom(Room room) 
	{
		this.room = room;
	}

	/**
	 * Player with turn getter.
	 * @return Player with turn
	 */
	public User getPlayerWithTurn() 
	{
		return players.get(playerTurn - 1);
	}

	/**
	 * Chosen tile getter.
	 * @return Chosen tile
	 */
	public Tile getChosenTile() 
	{
		return chosenTile;
	}

	/**
	 * Chosen tile setter.
	 * @param chosenTile Tile
	 */
	public void setChosenTile(Tile chosenTile) 
	{
		this.chosenTile = chosenTile;
	}

	/**
	 * Is during move getter
	 * @return True if during move, else false.
	 */
	public Boolean getDuringMove() 
	{
		return duringMove;
	}

	/**
	 * During move setter.
	 * @param duringMove Boolean value
	 */
	public void setDuringMove(Boolean duringMove) 
	{
		this.duringMove = duringMove;
	}

	/**
	 * Game status getter.
	 * @return Game status
	 */
	public GameStatus getGameStatus() 
	{
		return gameStatus;
	}

	/**
	 * Game status setter.
	 * @param gameStatus Game status
	 */
	public void setGameStatus(GameStatus gameStatus) 
	{
		this.gameStatus = gameStatus;
	}

	/**
	 * History getter.
	 * @return Game history
	 */
	public History getHistory() 
	{
		return history;
	}

	/**
	 * History setter.
	 * @param history History
	 */
	public void setHistory(History history) 
	{
		this.history = history;
	}

	/**
	 * Last update getter.
	 * @return Last update time
	 */
	public Date getLastUpdate() 
	{
		return lastUpdate;
	}

	/**
	 * LAst update setter.
	 * @param lastUpdate Date
	 */
	public void setLastUpdate(Date lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Game creation time getter.
	 * @return Game creation time
	 */
	public Date getCreateDate() 
	{
		return createDate;
	}

	/**
	 * Game creation date setter.
	 * @param createDate Date
	 */
	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}
}