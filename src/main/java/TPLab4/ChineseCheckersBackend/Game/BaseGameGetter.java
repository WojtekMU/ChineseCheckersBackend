package TPLab4.ChineseCheckersBackend.Game;

import TPLab4.ChineseCheckersBackend.GameFactory.*;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Base game getter class
 */
@Component
public class BaseGameGetter {
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
     * Game service
     */
    @Autowired
    private GameService gameService;

    /**
     * Game repository
     */
    @Autowired
    private GameRepository gameRepository;

    /**
     * Base game map
     */
    private static Map<String, Long> baseGameMap = new HashMap<String, Long>();

    /**
     * Base game map initializer
     */
    @PostConstruct
    private void initMap() {
        Set<Game> baseGames = gameRepository.findAllByGameStatus(GameStatus.BASE);

        gameRepository.deleteAll(baseGames);

        StandardTwoPlayersGame standardTwoPlayersGameBase = (StandardTwoPlayersGame) createBaseGame(standardTwoPlayersGameFactory);
        StandardThreePlayersGame standardThreePlayersGameBase = (StandardThreePlayersGame) createBaseGame(standardThreePlayersGameFactory);
        StandardFourPlayersGame standardFourPlayersGameBase = (StandardFourPlayersGame) createBaseGame(standardFourPlayersGameFactory);
        StandardSixPlayersGame standardSixPlayersGameBase = (StandardSixPlayersGame) createBaseGame(standardSixPlayersGameFactory);

        baseGameMap.put(StandardTwoPlayersGame.class.getSimpleName(), standardTwoPlayersGameBase.getId());
        baseGameMap.put(StandardThreePlayersGame.class.getSimpleName(), standardThreePlayersGameBase.getId());
        baseGameMap.put(StandardFourPlayersGame.class.getSimpleName(), standardFourPlayersGameBase.getId());
        baseGameMap.put(StandardSixPlayersGame.class.getSimpleName(), standardSixPlayersGameBase.getId());
    }

    /**
     * Method for creating a base game
     *
     * @param gameFactory Game factory
     * @return Game
     */
    private Game createBaseGame(GameFactory gameFactory) {
        Game game = gameFactory.createGame(new ArrayList<User>());
        gameService.setStatus(game, GameStatus.BASE);
        gameService.updateChosenTile(game, null);
        gameService.updateDuringMove(game, null);

        return game;
    }

    /**
     * Base game id getter
     *
     * @param gameType Game type
     * @return Base game id
     */
    public Long getBaseGame(String gameType) {
        return baseGameMap.get(gameType);
    }
}