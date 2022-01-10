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

import org.hibernate.annotations.CreationTimestamp;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Move.Move;
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
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    private Date createDate;
    
    @OneToMany(mappedBy="history")
    private List<Move> moves;
    
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
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}

	public List<Move> getMoves() 
	{
		return moves;
	}

	public void setMoves(List<Move> moves) 
	{
		this.moves = moves;
	};  
}
