package TPLab4.ChineseCheckersBackend.Move;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.History.HistoryService;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
import TPLab4.ChineseCheckersBackend.Request.EndTurnRequest;
import TPLab4.ChineseCheckersBackend.Room.RoomService;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.Room.RoomRepository;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;

/**
 * Move service class
 */
@Service
@Transactional
public class MoveService 
{
	/**
	 * Move repository
	 */
	@Autowired
	private MoveRepository moveRepository;

	/**
	 * Tile repository
	 */
	@Autowired
	private TileRepository tileRepository;

	/**
	 * Game repository
	 */
	@Autowired
	private GameRepository gameRepository;

	/**
	 * Game service
	 */
	@Autowired
	private GameService gameService;

	/**
	 * Room service
	 */
	@Autowired
	private RoomService roomService;

	/**
	 * History service
	 */
	@Autowired
	private HistoryService historyService;

	/**
	 * Move checker getter
	 */
	@Autowired
	private MoveCheckerGetter moveCheckerGetter;

	/**
	 * Method for performing a move.
	 * @param user User moving
	 * @param game Game
	 * @param tile Tile
	 * @param history History
	 */
	public void move(User user, Game game, Tile tile, History history)
	{
		Pair<Boolean, Boolean> result = moveCheckerGetter.getMoveChecker(game).checkMove(user, game, tile);

		if(result.getFirst())
		{
			saveMove(user, history, game.getChosenTile(), tile);

			TileColor firstTileColor = game.getChosenTile().getColor();

			game.getChosenTile().setColor(TileColor.WHITE);
			tile.setColor(firstTileColor);
			game.setChosenTile(tile);

			tileRepository.save(game.getChosenTile());
			tileRepository.save(tile);
			gameRepository.save(game);
		}

		if(result.getSecond())
		{
			this.endTurn(user, game, history);
		}
	}

	/**
	 * Method for ending the turn
	 * @param user User ending turn
	 * @param game Game
	 * @param history History
	 */
	public void endTurn(User user, Game game, History history)
	{
		if(moveCheckerGetter.getMoveChecker(game).checkEndTurn(user, game))
		{
			gameService.updateChosenTile(game, null);
			gameService.updateDuringMove(game, Boolean.FALSE);

			if(moveCheckerGetter.getMoveChecker(game).isCurrentPlayerWinner(game))
			{
				historyService.addPlayerToLeaderboard(history, user);
			}

			if(moveCheckerGetter.getMoveChecker(game).isGameFinished(game))
			{
				for(User p : game.getPlayers())
				{
					if(!history.getLeaderboard().contains(p))
					{
						historyService.addPlayerToLeaderboard(history, p);
					}
				}

				gameService.setStatus(game, GameStatus.FINISHED);
				roomService.detachGame(game.getRoom());
			}
			else
			{
				gameService.updatePlayerTurn(game);
			}
		}
	}

	/**
	 * Method for saving a move.
	 * @param user User moving
	 * @param history History
	 * @param firstTile First tile
	 * @param secondTile Second tile
	 */
	private void saveMove(User user, History history, Tile firstTile, Tile secondTile)
	{
		Move move = new Move();
		
		move.setPlayer(user);
		move.setHistory(history);
		move.setFirstTile(firstTile);
		move.setSecondTile(secondTile);
		
		moveRepository.save(move);
	}
}
