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

@Service
@Transactional
public class MoveService 
{
	@Autowired
	private MoveRepository moveRepository;

	@Autowired
	private TileRepository tileRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GameService gameService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private MoveCheckerGetter moveCheckerGetter;

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
