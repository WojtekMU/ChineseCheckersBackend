package TPLab4.ChineseCheckersBackend.History;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Move.Move;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomNotFoundException;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * History service class
 */
@Service
@Transactional
public class HistoryService 
{
    /**
     * History repository
     */
    @Autowired
    private HistoryRepository historyRepository;

    /**
     * Game repository
     */
    @Autowired
    private GameRepository gameRepository;

    /**
     * Method validating whether user has access to an element.
     * @param history History
     * @param user User
     * @throws AccessDeniedException If the user does not have access.
     */
    private void validate(History history, User user) throws AccessDeniedException
    {
        if(!history.getGame().getPlayers().contains(user))
        {
            throw new AccessDeniedException("User does not have access to this replay!");
        }
    }

    /**
     * Method loading history by id.
     * @param historyId History id
     * @return History
     * @throws HistoryNotFoundException When history was not found.
     */
    public History loadHistoryById(Long historyId) throws HistoryNotFoundException
    {
        History history = historyRepository.findById(historyId).orElseThrow(() -> new HistoryNotFoundException("History does not exist!"));

        return history;
    }

    /**
     * Method loading history by game id.
     * @param gameId Game id
     * @return History
     * @throws HistoryNotFoundException When history was not found.
     */
    public History loadHistoryByGameId(Long gameId) throws RoomNotFoundException
    {
        History history = historyRepository.findByGameId(gameId).orElseThrow(() -> new HistoryNotFoundException("History does not exist!"));

        return history;
    }

    /**
     * Method returning the leaderboard.
     * @param history History
     * @param user User
     * @return Game leaderboard
     */
    public List<User> getLeaderboard(History history, User user)
    {
        validate(history, user);

        return history.getLeaderboard();
    }

    /**
     * Method adding player to the leaderboard.
     * @param history History
     * @param user Player
     * @throws CannotAddPlayerToHistoryException When trying to add player to a leaderboard he already is in.
     */
    public void addPlayerToLeaderboard(History history, User user) throws CannotAddPlayerToHistoryException
    {
        if(history.getLeaderboard().contains(user))
        {
            throw new CannotAddPlayerToHistoryException("User is already on the leaderboard!");
        }

        history.getLeaderboard().add(user);
        historyRepository.save(history);
    }

    /**
     * Method for getting user replays.
     * @param user User
     * @return User replays
     */
    public List<History> getReplays(User user)
    {
        return historyRepository.findByLeaderboard_Id(user.getId());
    }

    /**
     * Method for getting replay moves.
     * @param history History
     * @param user User
     * @return Replay moves
     */
    public List<Move> getMoves(History history, User user)
    {
        validate(history, user);

        return history.getMoves();
    }

    /**
     * Method for getting replay board.
     * @param history History
     * @param user User
     * @return Replay board
     */
    public List<Tile> getReplayBoard(History history, User user)
    {
        validate(history, user);

        return history.getGame().getTileList();
    }
}
