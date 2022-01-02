package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class SixPlayerGameFactory extends GameFactory
{
	@Override
	public Game createGame(List<User> players) 
	{
		Game game = new Game();
		
		setGameProperties(players, game);
		
		game.setGameType(GameType.STANDARD_SIX_PLAYERS);

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