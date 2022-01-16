package TPLab4.ChineseCheckersBackend.MoveChecker;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.StandardFourPlayersGame;
import TPLab4.ChineseCheckersBackend.Game.StandardSixPlayersGame;
import TPLab4.ChineseCheckersBackend.Game.StandardThreePlayersGame;
import TPLab4.ChineseCheckersBackend.Game.StandardTwoPlayersGame;

/**
 * Move checker getter
 */
@Service
public class MoveCheckerGetter 
{
	/**
	 * Standard two players move checker
	 */
	@Autowired
	private StandardTwoPlayersMoveChecker standardTwoPlayersMoveChecker;

	/**
	 * Standard three players move checker
	 */
	@Autowired
	private StandardThreePlayersMoveChecker standardThreePlayersMoveChecker;

	/**
	 * Standard four players move checker
	 */
	@Autowired
	private StandardFourPlayersMoveChecker standardFourPlayersMoveChecker;

	/**
	 * Standard six players move checker
	 */
	@Autowired
	private StandardSixPlayersMoveChecker standardSixPlayersMoveChecker;
	
	private static final Map<Class<? extends Game>, AbstractMoveChecker> moveCheckerMap = new HashMap<Class<? extends Game>, AbstractMoveChecker>();

	/**
	 * Method initialising move checker map
	 */
	@PostConstruct
	private void initializeMap()
	{
		moveCheckerMap.put(StandardTwoPlayersGame.class, standardTwoPlayersMoveChecker);
		moveCheckerMap.put(StandardThreePlayersGame.class, standardThreePlayersMoveChecker);
		moveCheckerMap.put(StandardFourPlayersGame.class, standardFourPlayersMoveChecker);
		moveCheckerMap.put(StandardSixPlayersGame.class, standardSixPlayersMoveChecker);
	}

	/**
	 * Method returning move checker depending on game type.
	 * @param game Game
	 * @return Move checker
	 */
	public AbstractMoveChecker getMoveChecker(Game game)
	{
		 return moveCheckerMap.get(game.getClass());
	}
}
