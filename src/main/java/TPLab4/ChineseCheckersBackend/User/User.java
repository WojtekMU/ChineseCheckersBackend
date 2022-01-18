package TPLab4.ChineseCheckersBackend.User;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.Move.Move;
import TPLab4.ChineseCheckersBackend.Role.Role;
import TPLab4.ChineseCheckersBackend.Room.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the user.
 */
@Entity
public class User {
    /**
     * Id of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Username of the user.
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Hashed password of the user.
     */
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * User roles.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    /**
     * User history.
     */
    @ManyToMany(mappedBy = "leaderboard")
    @JsonIgnore
    Set<History> history = new HashSet<History>();

    /**
     * User games.
     */
    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    Set<Game> games = new HashSet<Game>();

    /**
     * User rooms.
     */
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    Set<Room> rooms = new HashSet<Room>();

    /**
     * User moves from games.
     */
    @OneToMany(mappedBy = "player")
    @JsonIgnore
    private Set<Move> moves;

    /**
     * Empty user constructor.
     */
    public User() {
    }

    /**
     * User constructor.
     *
     * @param username Username
     * @param password HAshed password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Id getter.
     *
     * @return User Id
     */
    public Long getId() {
        return id;
    }

    /**
     * Username getter.
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username setter.
     *
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Password getter.
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * User history getter.
     *
     * @return History
     */
    public Set<History> getHistory() {
        return history;
    }

    /**
     * User history setter.
     *
     * @param history History
     */
    public void setHistory(Set<History> history) {
        this.history = history;
    }

    /**
     * User rooms getter.
     *
     * @return User rooms
     */
    public Set<Room> getRooms() {
        return rooms;
    }

    /**
     * User roles getter.
     *
     * @return User roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * User roles setter.
     *
     * @param roles User roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * User games getter.
     *
     * @return User games set
     */
    public Set<Game> getGames() {
        return games;
    }

    /**
     * User moves getter.
     *
     * @return USer moves set
     */
    public Set<Move> getMoves() {
        return moves;
    }

    /**
     * User id setter.
     *
     * @param id Long value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * User password setter
     *
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * User games setter.
     *
     * @param games Games set
     */
    public void setGames(Set<Game> games) {
        this.games = games;
    }

    /**
     * User rooms setter.
     *
     * @param rooms Rooms set
     */
    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * User moves setter.
     *
     * @param moves Moves set
     */
    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((User) o).id;
    }
}
