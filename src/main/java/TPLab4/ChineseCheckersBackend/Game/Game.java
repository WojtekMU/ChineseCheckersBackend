package TPLab4.ChineseCheckersBackend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Game 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "second_player_id", nullable = true)
    private User secondPlayer;

    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false)
    private User firstPlayer;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    public Game() {};
    
	public Game(User secondPlayer, User firstPlayer, GameStatus gameStatus) 
	{
		this.secondPlayer = secondPlayer;
		this.firstPlayer = firstPlayer;
		this.gameStatus = gameStatus;
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
}