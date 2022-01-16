package TPLab4.ChineseCheckersBackend.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.UpdateTimestamp;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.User.User;

/**
 * Class representing a room.
 */
@Entity
public class Room 
{
	/**
	 * Room id.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

	/**
	 * Users in room.
	 */
    @ManyToMany
    @JoinTable(
    		  name = "room_user", 
    		  joinColumns = @JoinColumn(name = "room_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "user_id"))
    @OrderColumn
    private List<User> users = new ArrayList<User>();

	/**
	 * Boolean value representing whether a game already started in the room.
	 */
	@Column(name = "game_started")
    private Boolean gameStarted;

	/**
	 * Linked game id.
	 */
	@OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

	/**
	 * Last update time.
	 */
	@UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

	/**
	 * Empty room constructor.
	 */
	public Room() {}

	/**
	 * Room id getter.
	 * @return Room id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * Room id setter.
	 * @param id Long value
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * User list getter.
	 * @return Users in room
	 */
	public List<User> getUsers()
	{
		return users;
	}

	/**
	 * GameStarted getter.
	 * @return Boolean value representing whether a game started or not.
	 */
	public Boolean getGameStarted() 
	{
		return gameStarted;
	}

	/**
	 * Game started setter.
	 * @param gameStarted True or false
	 */
	public void setGameStarted(Boolean gameStarted) 
	{
		this.gameStarted = gameStarted;
	}

	/**
	 * Game getter.
	 * @return Game
	 */
	public Game getGame() 
	{
		return game;
	}

	/**
	 * Game setter.
	 * @param game Game
	 */
	public void setGame(Game game) 
	{
		this.game = game;
	}

	/**
	 * Boolean representing whether game started getter.
	 * @return Boolean representing whether game started.
	 */
	public Boolean isGameStarted() 
	{
		return gameStarted;
	}

	/**
	 * Last update time getter.
	 * @return Last update time
	 */
	public Date getLastUpdate() 
	{
		return lastUpdate;
	}

	/**
	 * Last update time setter.
	 * @param lastUpdate Date
	 */
	public void setLastUpdate(Date lastUpdate) 
	{
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Users in room setter.
	 * @param users User list
	 */
	public void setUsers(List<User> users)
	{
		this.users = users;
	}
}
