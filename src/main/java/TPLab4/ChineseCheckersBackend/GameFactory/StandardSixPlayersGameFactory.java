package TPLab4.ChineseCheckersBackend.GameFactory;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.StandardSixPlayersGame;
import TPLab4.ChineseCheckersBackend.User.User;

/**
 * Standard six players game factory class
 */
@Component
public class StandardSixPlayersGameFactory extends GameFactory
{
	@Override
	public StandardSixPlayersGame createGame(List<User> players)
	{
		StandardSixPlayersGame game = new StandardSixPlayersGame();
		
		setGameProperties(players, game);

		gameRepository.save(game);
		
		fillFirstCorner(game, colorOrder.get(1));
		fillSixthCorner(game, colorOrder.get(2));
		fillFifthCorner(game, colorOrder.get(3));
		fillFourthCorner(game, colorOrder.get(4));
		fillThirdCorner(game, colorOrder.get(5));
		fillSecondCorner(game, colorOrder.get(6));
		
		gameRepository.save(game);
		
		return game;
	}
}