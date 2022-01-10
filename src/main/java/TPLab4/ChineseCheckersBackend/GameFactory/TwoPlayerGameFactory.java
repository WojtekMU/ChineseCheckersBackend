package TPLab4.ChineseCheckersBackend.GameFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.StandardTwoPlayersGame;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardThreePlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardTwoPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class TwoPlayerGameFactory extends GameFactory
{
	@Override
	public Game createGame(List<User> players) 
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
