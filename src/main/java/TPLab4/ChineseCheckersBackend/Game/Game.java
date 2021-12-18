package TPLab4.ChineseCheckersBackend.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Transient;

import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;

@Entity
public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = true)
    private User firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id", nullable = true)
    private User secondPlayer;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    
    @OneToMany(mappedBy="game")
    private List<Tile> tileList = new ArrayList<Tile>();

    public Game() {};

	public Game(Long id, User firstPlayer, User secondPlayer, User thirdPlayer, User fourthPlayer, User fifthPlayer,
			User sixthPlayer, GameStatus gameStatus, List<Tile> tileList) 
	{
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.gameStatus = gameStatus;
		this.tileList = tileList;
	}



	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public User getSecondPlayer() 
	{
		return secondPlayer;
	}

	public void setSecondPlayer(User secondPlayer) 
	{
		this.secondPlayer = secondPlayer;
	}

	public User getFirstPlayer() 
	{
		return firstPlayer;
	}

	public void setFirstPlayer(User firstPlayer) 
	{
		this.firstPlayer = firstPlayer;
	}

	public GameStatus getGameStatus() 
	{
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) 
	{
		this.gameStatus = gameStatus;
	}
	
	public List<Tile> getTileList() 
	{
		return tileList;
	}

	public void setTileList(List<Tile> tileList) 
	{
		this.tileList = tileList;
	}
}