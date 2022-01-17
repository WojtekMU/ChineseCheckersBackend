package TPLab4.ChineseCheckersBackend.History;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Move.Move;
import TPLab4.ChineseCheckersBackend.User.User;

/**
 * Class representing history
 */
@Entity
public class History 
{
	/**
	 * History id
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

	/**
	 * Game type
	 */
	@Column
	private String gameType;

	/**
	 * Game attached to history/
	 */
	@OneToOne(mappedBy = "history")
	@JsonIgnore
    private Game game;

	/**
	 * Game leaderboard.
	 */
    @ManyToMany
    @JoinTable(
    		  name = "history_user", 
    		  joinColumns = @JoinColumn(name = "history_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "user_id"))
	@OrderColumn
    List<User> leaderboard = new ArrayList<User>();

	/**
	 * History creation timestamp
	 */
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    private Date createDate;

	/**
	 * Moves list
	 */
	@OneToMany(mappedBy="history")
    private List<Move> moves;

	/**
	 * Empty history constructor.
	 */
	public History() {}

	/**
	 * History id getter.
	 * @return History id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * History id setter.
	 * @param id History id
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * History game getter.
	 * @return History game
	 */
	public Game getGame() 
	{
		return game;
	}

	/**
	 * History game setter.
	 * @param game Game
	 */
	public void setGame(Game game) 
	{
		this.game = game;
	}

	/**
	 * Leaderboard getter.
	 * @return Leaderboard
	 */
	public List<User> getLeaderboard() 
	{
		return leaderboard;
	}

	/**
	 * Leaderboard setter.
	 * @param leaderboard User list
	 */
	public void setLeaderboard(List<User> leaderboard) 
	{
		this.leaderboard = leaderboard;
	}

	/**
	 * History creation date getter.
	 * @return History creation date.
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * History creation date setter.
	 * @param createDate Date
	 */
	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}

	/**
	 * History moves getter.
	 * @return History moves
	 */
	public List<Move> getMoves() 
	{
		return moves;
	}

	/**
	 * History moves setter.
	 * @param moves Moves list
	 */
	public void setMoves(List<Move> moves) 
	{
		this.moves = moves;
	}

	/**
	 * Game type getter.
	 * @return Game type
	 */
	public String getGameType()
	{
		return gameType;
	}

	/**
	 * Game type setter.
	 * @param gameType Game type
	 */
	public void setGameType(String gameType)
	{
		this.gameType = gameType;
	}
}
