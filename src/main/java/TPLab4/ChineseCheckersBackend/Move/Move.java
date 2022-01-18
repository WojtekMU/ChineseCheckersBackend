package TPLab4.ChineseCheckersBackend.Move;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Class representing moves.
 */
@Entity
public class Move {
    /**
     * Move id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Player who performed the move.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    /**
     * History where the move is.
     */
    @ManyToOne
    @JoinColumn(name = "history_id")
    @JsonIgnore
    private History history;

    @Column(name = "first_tile_x")
    private Long firstTileX;

    @Column(name = "first_tile_y")
    private Long firstTileY;

    @Column(name = "second_tile_x")
    private Long secondTileX;

    @Column(name = "second_tile_y")
    private Long secondTileY;

    /**
     * Move id getter.
     *
     * @return Move id
     */
    public Long getId() {
        return id;
    }

    /**
     * Move id setter.
     *
     * @param id Move id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Move player getter.
     *
     * @return Move player
     */
    public User getPlayer() {
        return player;
    }

    /**
     * Move player setter.
     *
     * @param player Player
     */
    public void setPlayer(User player) {
        this.player = player;
    }

    /**
     * Move history getter.
     *
     * @return Move history
     */
    public History getHistory() {
        return history;
    }

    /**
     * Move history setter.
     *
     * @param history History
     */
    public void setHistory(History history) {
        this.history = history;
    }

    /**
     * First tile x getter.
     *
     * @return First tile x
     */
    public Long getFirstTileX() {
        return firstTileX;
    }

    /**
     * First tile x setter.
     *
     * @param firstTileX X coordinate
     */
    public void setFirstTileX(Long firstTileX) {
        this.firstTileX = firstTileX;
    }

    /**
     * First tile y getter.
     *
     * @return First tile y
     */
    public Long getFirstTileY() {
        return firstTileY;
    }

    /**
     * First tile y setter.
     *
     * @param firstTileY Y coordinate
     */
    public void setFirstTileY(Long firstTileY) {
        this.firstTileY = firstTileY;
    }

    /**
     * Second tile x getter.
     *
     * @return Second tile x
     */
    public Long getSecondTileX() {
        return secondTileX;
    }

    /**
     * Second tile x setter.
     *
     * @param secondTileX X coordinate
     */
    public void setSecondTileX(Long secondTileX) {
        this.secondTileX = secondTileX;
    }

    /**
     * Second tile y getter.
     *
     * @return Second tile y
     */
    public Long getSecondTileY() {
        return secondTileY;
    }

    /**
     * Second tile y setter.
     *
     * @param secondTileY Y coordinate
     */
    public void setSecondTileY(Long secondTileY) {
        this.secondTileY = secondTileY;
    }
}
