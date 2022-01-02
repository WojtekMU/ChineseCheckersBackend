package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class FourPlayerGameFactory extends GameFactory
{
	@Override
	public Game createGame(List<User> players) 
	{
		Game game = new Game();
		
		setGameProperties(players, game);
		
		game.setGameType(GameType.STANDARD_FOUR_PLAYERS);
		
		gameRepository.save(game);
		
		fillSixthCorner(game, colorOrder.get(1));
		fillFifthCorner(game, colorOrder.get(2));
		fillThirdCorner(game, colorOrder.get(3));
		fillSecondCorner(game, colorOrder.get(4));
		
		gameRepository.save(game);
		
		return game;
	}
}