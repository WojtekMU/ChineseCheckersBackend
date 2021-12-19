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
		
		game.getPlayers().addAll(players);
		game.setGameStatus(GameStatus.WAITING_FOR_PLAYERS);
		game.setPlayerTurn(1L);
		createClearBoard(game);
		
		gameRepository.save(game);
		
		fillFirstCorner(game, colorOrder.get(1));
		fillSecondCorner(game, colorOrder.get(2));
		fillFourthCorner(game, colorOrder.get(3));
		fillFifthCorner(game, colorOrder.get(4));
		
		gameRepository.save(game);
		
		return game;
	}
}