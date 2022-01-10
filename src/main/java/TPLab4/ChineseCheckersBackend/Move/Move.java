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

@Entity
public class Move 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User player;
	
	@ManyToOne
    @JoinColumn(name = "history_id")
	@JsonIgnore
    private History history;
	
	@ManyToOne
    @JoinColumn(name = "first_tile_id")
    private Tile firstTile;
    
	@ManyToOne
    @JoinColumn(name = "second_tile_id")
    private Tile secondTile;

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public User getPlayer()
	{
		return player;
	}

	public void setPlayer(User player) 
	{
		this.player = player;
	}

	public History getHistory()
	{
		return history;
	}

	public void setHistory(History history)
	{
		this.history = history;
	}

	public Tile getFirstTile() 
	{
		return firstTile;
	}

	public void setFirstTile(Tile firstTile) 
	{
		this.firstTile = firstTile;
	}

	public Tile getSecondTile()
	{
		return secondTile;
	}

	public void setSecondTile(Tile secondTile)
	{
		this.secondTile = secondTile;
	}
}
