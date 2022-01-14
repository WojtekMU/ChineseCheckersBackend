package TPLab4.ChineseCheckersBackend.History;

import TPLab4.ChineseCheckersBackend.Game.BaseGameGetter;
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

@Service
@Transactional
public class HistoryService 
{
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BaseGameGetter baseGameGetter;

    private void validate(History history, User user) throws AccessDeniedException
    {
        if(!history.getGame().getPlayers().contains(user))
        {
            throw new AccessDeniedException("User does not have access to this replay!");
        }
    }

    public History loadHistoryById(Long historyId) throws HistoryNotFoundException
    {
        History history = historyRepository.findById(historyId).orElseThrow(() -> new HistoryNotFoundException("History does not exist!"));

        return history;
    }

    public History loadHistoryByGameId(Long gameId) throws RoomNotFoundException
    {
        History history = historyRepository.findByGameId(gameId).orElseThrow(() -> new HistoryNotFoundException("History does not exist!"));

        return history;
    }

    public List<User> getLeaderboard(History history, User user)
    {
        validate(history, user);

        return history.getLeaderboard();
    }

    public void addPlayerToLeaderboard(History history, User user) throws AccessDeniedException
    {
        if(history.getLeaderboard().contains(user))
        {
            throw new AccessDeniedException("User is already on the leaderboard!");
        }

        history.getLeaderboard().add(user);
        historyRepository.save(history);
    }

    public List<History> getReplays(User user)
    {
        return historyRepository.findByLeaderboard_Id(user.getId());
    }

    public List<Move> getMoves(History history, User user)
    {
        validate(history, user);

        return history.getMoves();
    }

    public List<Tile> getReplayBoard(History history, User user)
    {
        validate(history, user);

        Game game = gameRepository.getById(baseGameGetter.getBaseGame(history.getGame()));

        return game.getTileList();
    }
}
