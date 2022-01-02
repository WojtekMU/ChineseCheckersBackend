package TPLab4.ChineseCheckersBackend.History;

import java.util.ArrayList;
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

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.User.User;

@Entity
public class History 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@OneToOne
	@JoinColumn(name = "game_id")
    private Game game;
	
    @ManyToMany
    @JoinTable(
    		  name = "history_user", 
    		  joinColumns = @JoinColumn(name = "history_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> leaderboard = new ArrayList<User>();	
    
    public History() {}

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Game getGame() 
	{
		return game;
	}

	public void setGame(Game game) 
	{
		this.game = game;
	}

	public List<User> getLeaderboard() 
	{
		return leaderboard;
	}

	public void setLeaderboard(List<User> leaderboard) 
	{
		this.leaderboard = leaderboard;
	};  
}
