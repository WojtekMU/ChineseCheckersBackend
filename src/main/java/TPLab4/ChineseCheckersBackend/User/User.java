package TPLab4.ChineseCheckersBackend.User;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.Room.Room;

@Entity
public class User 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "user_name", unique = true, nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
    @ManyToMany(mappedBy = "leaderboard")
    @JsonIgnore
    Set<History> history = new HashSet<History>();
	
    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    Set<Game> games = new HashSet<Game>();
    
    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    Set<Room> rooms = new HashSet<Room>();
	
    public User() {}

    public User(String username, String password) 
    {
        this.username = username;
        this.password = password;
    }

	public Long getId() 
	{
		return id;
	}

	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}

	public Set<Game> getGames() 
	{
		return games;
	}

	public void setGames(Set<Game> games) 
	{
		this.games = games;
	}
	
	public Set<History> getHistory() 
	{
		return history;
	}

	public void setHistory(Set<History> history) 
	{
		this.history = history;
	}

	public Set<Room> getRooms() 
	{
		return rooms;
	}

	public void setRooms(Set<Room> rooms) 
	{
		this.rooms = rooms;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	@Override
	public boolean equals(Object o)
	{
		return this.id == ((User) o).id;
	}
}
