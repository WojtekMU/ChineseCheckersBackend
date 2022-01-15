package TPLab4.ChineseCheckersBackend.GameFactory;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.StandardThreePlayersGame;
import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class StandardThreePlayersGameFactory extends GameFactory
{
	@Override
	public StandardThreePlayersGame createGame(List<User> players)
	{
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
