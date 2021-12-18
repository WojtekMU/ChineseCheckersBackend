package TPLab4.ChineseCheckersBackend.Tile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.Game.Game;

@Entity
public class Tile implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "x", nullable = false)
	private Long x;
	
	@Column(name = "y", nullable = false)
	private Long y;
	
	@Column(name = "color", nullable = false)
	private String color;	
	
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;
	
    public Tile() {};
    
	public Tile(Long x, Long y, String color, Game game) 
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.game = game;
	}

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getX() 
	{
		return x;
	}
	
	public void setX(Long x) 
	{
		this.x = x;
	}
	
	public Long getY() 
	{
		return y;
	}
	
	public void setY(Long y) 
	{
		this.y = y;
	}
	
	public String getColor() 
	{
		return color;
	}
	
	public void setColor(String color) 
	{
		this.color = color;
	}

	public Game getGame() 
	{
		return game;
	}

	public void setGame(Game game) 
	{
		this.game = game;
	}
}
