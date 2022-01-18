package TPLab4.ChineseCheckersBackend.GameFactory;

import TPLab4.ChineseCheckersBackend.Game.StandardThreePlayersGame;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Standard three players game factory class
 */
@Component
public class StandardThreePlayersGameFactory extends GameFactory {
    @Override
    public StandardThreePlayersGame createGame(List<User> players) {
        StandardThreePlayersGame game = new StandardThreePlayersGame();

        setGameProperties(players, game);

        gameRepository.save(game);

        fillFirstCorner(game, colorOrder.get(1));
        fillFifthCorner(game, colorOrder.get(2));
        fillThirdCorner(game, colorOrder.get(3));

        gameRepository.save(game);

        return game;
    }
}
