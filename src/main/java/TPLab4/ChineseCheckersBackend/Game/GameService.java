package TPLab4.ChineseCheckersBackend.Game;

import TPLab4.ChineseCheckersBackend.GameFactory.StandardFourPlayersGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardSixPlayersGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardThreePlayersGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardTwoPlayersGameFactory;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Game service class
 */
@Service
@Transactional
public class GameService {
    /**
     * Game repository
     */
    @Autowired
    private GameRepository gameRepository;

    /**
     * Tile repository
     */
    @Autowired
    private TileRepository tileRepository;

    /**
     * History repository
     */
    @Autowired
    private HistoryRepository historyRepository;

    /**
     * Standard two players game factory
     */
    @Autowired
    private StandardTwoPlayersGameFactory standardTwoPlayersGameFactory;

    /**
     * Standard three players game factory
     */
    @Autowired
    private StandardThreePlayersGameFactory standardThreePlayersGameFactory;

    /**
     * Standard four players game factory
     */
    @Autowired
    private StandardFourPlayersGameFactory standardFourPlayersGameFactory;

    /**
     * Standard six players game factory
     */
    @Autowired
    private StandardSixPlayersGameFactory standardSixPlayersGameFactory;

    /**
     * Method validating whether user has access to an element.
     *
     * @param game Game
     * @param user User
     * @throws AccessDeniedException If the user does not have access.
     */
    private void validate(Game game, User user) throws AccessDeniedException {
        if (!game.getPlayers().contains(user)) {
            throw new AccessDeniedException("User does not belong to this game!");
        }
    }

    /**
     * Method for loading game by id.
     *
     * @param gameId Game id
     * @return Game
     * @throws GameNotFoundException When game was not found.d
     */
    public Game loadGameById(Long gameId) throws GameNotFoundException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game does not exist!"));

        return game;
    }

    /**
     * Method for creating a game.
     *
     * @param players Player list
     * @param user    User requesting game creation.
     * @return Game
     * @throws AccessDeniedException   When user is not allowed to create a game.
     * @throws CantCreateGameException When game can not be created.
     */
    public Game createGame(List<User> players, User user) throws AccessDeniedException, CantCreateGameException {
        if (!players.contains(user)) {
            throw new AccessDeniedException("Cannot start game!");
        }

        if (!players.get(0).equals(user)) {
            throw new CantCreateGameException("You are not the host!");
        }

        if (players.size() == 2) {
            return standardTwoPlayersGameFactory.createGame(players);
        } else if (players.size() == 3) {
            return standardThreePlayersGameFactory.createGame(players);
        } else if (players.size() == 4) {
            return standardFourPlayersGameFactory.createGame(players);
        } else if (players.size() == 6) {
            return standardSixPlayersGameFactory.createGame(players);
        } else {
            throw new CantCreateGameException("Wrong player number!");
        }
    }

    /**
     * Method for updating turn.
     *
     * @param game Game
     * @return Player turn
     */
    public Integer updatePlayerTurn(Game game) {
        Integer playerTurn = game.getPlayerTurn();
        History history = game.getHistory();

        do {
            if (game.getPlayers().size() == playerTurn) {
                playerTurn = 1;
            } else {
                playerTurn++;
            }

            game.setPlayerTurn(playerTurn);
        }
        while (history.getLeaderboard().contains(game.getPlayerWithTurn()));

        gameRepository.save(game);

        return playerTurn;
    }

    /**
     * Method for updating chosen tile.
     *
     * @param game Game
     * @param tile Tile
     */
    public void updateChosenTile(Game game, Tile tile) {
        game.setChosenTile(tile);

        gameRepository.save(game);
    }

    /**
     * Method for updating during move.
     *
     * @param game Game
     * @param bool Boolean value
     */
    public void updateDuringMove(Game game, Boolean bool) {
        game.setDuringMove(bool);

        gameRepository.save(game);
    }

    /**
     * Method for getting the game board.
     *
     * @param game Game
     * @param user User requesting
     * @return Game board
     * @throws AccessDeniedException When user is not allowed to get the element.
     */
    public List<Tile> getBoard(Game game, User user) throws AccessDeniedException {
        validate(game, user);

        return game.getTileList();
    }

    /**
     * Method for getting last update.
     *
     * @param game Game
     * @param user User requesting
     * @return Last update
     * @throws AccessDeniedException When user is not allowed to get the element.
     */
    public Date getLastUpdate(Game game, User user) throws AccessDeniedException {
        validate(game, user);

        return game.getLastUpdate();
    }

    /**
     * Method for getting game status.
     *
     * @param game Game
     * @param user User requesting
     * @return Game status
     * @throws AccessDeniedException When user is not allowed to get the element.
     */
    public GameStatus getGameStatus(Game game, User user) throws AccessDeniedException {
        validate(game, user);

        return game.getGameStatus();
    }

    /**
     * Method for getting player turn.
     *
     * @param game Game
     * @param user User requesting
     * @return Player turns
     * @throws AccessDeniedException When user is not allowed to get the element.
     */
    public Integer getPlayerTurn(Game game, User user) throws AccessDeniedException {
        validate(game, user);

        return game.getPlayerTurn();
    }

    /**
     * Method for getting chosen tile.
     *
     * @param game Game
     * @param user User requesting
     * @return Chosen tile
     * @throws AccessDeniedException When user is not allowed to get the element.
     */
    public Tile getChosenTile(Game game, User user) throws AccessDeniedException {
        validate(game, user);

        return game.getChosenTile();
    }

    /**
     * Method for getting the player board.
     *
     * @param game Game
     * @param user User requesting
     * @return Player board
     * @throws AccessDeniedException When user is not allowed to get the element.
     */
    public List<User> getPlayerBoard(Game game, User user) throws AccessDeniedException {
        validate(game, user);

        return game.getPlayers();
    }

    /**
     * Method for setting the game status.
     *
     * @param game       Game
     * @param gameStatus Game status
     */
    public void setStatus(Game game, GameStatus gameStatus) {
        game.setGameStatus(gameStatus);

        gameRepository.save(game);
    }

    /**
     * Method for setting history.
     *
     * @param game    Game
     * @param history History
     */
    public void setHistory(Game game, History history) {
        game.setHistory(history);

        gameRepository.save(game);
    }

    /**
     * Method for deleting finished games.
     */
    @PostConstruct
    @Scheduled(fixedDelay = 5000)
    public void deleteFinishedGames() {
        Set<Game> finishedGames = gameRepository.findAllByGameStatus(GameStatus.FINISHED);
        Date now = new Date();

        for (Game game : finishedGames) {
            Duration duration = Duration.between(now.toInstant(), game.getLastUpdate().toInstant());
            long diff = Math.abs(duration.toSeconds());

            if (diff >= 5) {
                gameRepository.delete(game);
            }
        }
    }
}