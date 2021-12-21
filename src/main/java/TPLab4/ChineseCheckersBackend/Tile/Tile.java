package TPLab4.ChineseCheckersBackend.Tile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.Game.Game;

@Entity
public class Tile
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "x", nullable = false)
	private Long x;
	
	@Column(name = "y", nullable = false)
	private Long y;
	
	@Enumerated(EnumType.STRING)
	private TileColor color;	
	
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;
	
    @OneToOne(mappedBy = "chosenTile")
    @JsonIgnore
    private Game chosenTileGame;
    
    public Tile() {};
    
	public Tile(Long x, Long y, String color, Game game) 
	{
		this.x = x;
		this.y = y;
		this.color = TileColor.WHITE;
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
	
	public TileColor getColor() 
	{
		return color;
	}
	
	public void setColor(TileColor color) 
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

	public Game getChosenTileGame() 
	{
		return chosenTileGame;
	}

	public void setChosenTileGame(Game chosenTileGame) 
	{
		this.chosenTileGame = chosenTileGame;
	}
}
