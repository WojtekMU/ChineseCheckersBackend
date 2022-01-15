package TPLab4.ChineseCheckersBackend.GameFactory;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.StandardFourPlayersGame;
import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class StandardFourPlayersGameFactory extends GameFactory
{
	@Override
	public StandardFourPlayersGame createGame(List<User> players)
	{
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