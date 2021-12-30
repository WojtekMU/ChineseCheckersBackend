package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class TwoPlayerGameFactory extends GameFactory
{
	@Override
	public Game createGame(List<User> players) 
	{
		Game game = new Game();
		
		game.getPlayers().addAll(players);
		game.setGameStatus(GameType.WAITING_FOR_PLAYERS);
		game.setPlayerTurn(1);
		game.setChosenTile(null);
		game.setDuringMove(false);
		createClearBoard(game);
		
		gameRepository.save(game);
		
		fillFirstCorner(game, colorOrder.get(1));
		fillFourthCorner(game, colorOrder.get(2));
		
		gameRepository.save(game);
		
		return game;
	}
}
