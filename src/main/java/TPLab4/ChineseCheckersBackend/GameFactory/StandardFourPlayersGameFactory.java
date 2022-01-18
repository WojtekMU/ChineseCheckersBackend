package TPLab4.ChineseCheckersBackend.GameFactory;

import TPLab4.ChineseCheckersBackend.Game.StandardFourPlayersGame;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Standard four players game factory class
 */
@Component
public class StandardFourPlayersGameFactory extends GameFactory {
    @Override
    public StandardFourPlayersGame createGame(List<User> players) {
        StandardFourPlayersGame game = new StandardFourPlayersGame();

        setGameProperties(players, game);

        gameRepository.save(game);

        fillSixthCorner(game, colorOrder.get(1));
        fillFifthCorner(game, colorOrder.get(2));
        fillThirdCorner(game, colorOrder.get(3));
        fillSecondCorner(game, colorOrder.get(4));

        gameRepository.save(game);

        return game;
    }
}