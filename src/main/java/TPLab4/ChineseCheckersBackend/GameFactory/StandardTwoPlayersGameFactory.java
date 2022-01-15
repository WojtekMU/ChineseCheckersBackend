package TPLab4.ChineseCheckersBackend.GameFactory;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.StandardTwoPlayersGame;
import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class StandardTwoPlayersGameFactory extends GameFactory
{
	@Override
	public StandardTwoPlayersGame createGame(List<User> players)
	{
		StandardTwoPlayersGame game = new StandardTwoPlayersGame();
		
		setGameProperties(players, game);
		
		gameRepository.save(game);
		
		fillFirstCorner(game, colorOrder.get(1));
		fillFourthCorner(game, colorOrder.get(2));
		
		gameRepository.save(game);
		
		return game;
	}
}
