package TPLab4.ChineseCheckersBackend.Room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.User.User;

@Entity
public class Room 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
    @ManyToMany
    @JoinTable(
    		  name = "room_user", 
    		  joinColumns = @JoinColumn(name = "room_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> players = new ArrayList<User>();
    
    @Column(name = "game_started")
    private Boolean gameStarted;
    
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;
    
    public Room() {}

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public List<User> getPlayers() 
	{
		return players;
	}

	public void setPlayers(List<User> players) 
	{
		this.players = players;
	}

	public void setGameStarted(Boolean gameStarted) 
	{
		this.gameStarted = gameStarted;
	}

	public Game getGame() 
	{
		return game;
	}

	public void setGame(Game game) 
	{
		this.game = game;
	}

	public Boolean isGameStarted() 
	{
		return gameStarted;
	}	
}
