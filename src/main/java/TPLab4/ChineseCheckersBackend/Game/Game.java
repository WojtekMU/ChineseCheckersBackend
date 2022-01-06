package TPLab4.ChineseCheckersBackend.Game;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.MoveChecker.AbstractMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardFourPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardSixPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardThreePlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardTwoPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;

@Entity
public abstract class Game
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	protected Long id;
	
    @ManyToMany
    @JoinTable(
    		  name = "game_user", 
    		  joinColumns = @JoinColumn(name = "game_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "user_id"))
    @OrderColumn
    protected List<User> players = new ArrayList<User>();	
    
    @Enumerated(EnumType.STRING)
    protected GameStatus gameStatus;
    
    @OneToMany(mappedBy="game")
    protected List<Tile> tileList = new ArrayList<Tile>();

    @Column(name = "player_turn")
    protected Integer playerTurn;
    
    @OneToOne(mappedBy = "game")
    @JsonIgnore
    protected Room room;
    
    @OneToOne(mappedBy = "game")
    @JsonIgnore
    protected History history;
    
    @OneToOne
    @JoinColumn(name = "tile_id")
    protected Tile chosenTile;
    
    @Column(name = "during_move")
    protected Boolean duringMove;

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public List<Tile> getTileList() 
	{
		return tileList;
	}

	public void setTileList(List<Tile> tileList) 
	{
		this.tileList = tileList;
	}

	public List<User> getPlayers() 
	{
		return players;
	}

	public void setPlayers(List<User> players) 
	{
		this.players = players;
	}

	public Integer getPlayerTurn() 
	{
		return playerTurn;
	}

	public void setPlayerTurn(Integer playerTurn) 
	{
		this.playerTurn = playerTurn;
	}

	public Room getRoom() 
	{
		return room;
	}

	public void setRoom(Room room) 
	{
		this.room = room;
	}
	
	public User getPlayerWithTurn() 
	{
		return players.get(playerTurn - 1);
	}

	public Tile getChosenTile() 
	{
		return chosenTile;
	}

	public void setChosenTile(Tile chosenTile) 
	{
		this.chosenTile = chosenTile;
	}

	public Boolean getDuringMove() 
	{
		return duringMove;
	}

	public void setDuringMove(Boolean duringMove) 
	{
		this.duringMove = duringMove;
	}

	public GameStatus getGameStatus() 
	{
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) 
	{
		this.gameStatus = gameStatus;
	}

	public History getHistory() 
	{
		return history;
	}

	public void setHistory(History history) 
	{
		this.history = history;
	}
}