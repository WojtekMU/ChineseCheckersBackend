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

@Service
public class MoveCheckerGetter 
{
	@Autowired
	private StandardTwoPlayersMoveChecker standardTwoPlayersMoveChecker;
	
	@Autowired
	private StandardThreePlayersMoveChecker standardThreePlayersMoveChecker;
	
	@Autowired
	private StandardFourPlayersMoveChecker standardFourPlayersMoveChecker;
	
	@Autowired
	private StandardSixPlayersMoveChecker standardSixPlayersMoveChecker;
	
	private static Map<Class<? extends Game>, AbstractMoveChecker> moveCheckerMap = new HashMap<Class<? extends Game>, AbstractMoveChecker>();
	
	@PostConstruct
	private void initializeMap()
	{
		moveCheckerMap.put(StandardTwoPlayersGame.class, standardTwoPlayersMoveChecker);
		moveCheckerMap.put(StandardThreePlayersGame.class, standardThreePlayersMoveChecker);
		moveCheckerMap.put(StandardFourPlayersGame.class, standardFourPlayersMoveChecker);
		moveCheckerMap.put(StandardSixPlayersGame.class, standardSixPlayersMoveChecker);
	}
	
	public AbstractMoveChecker getMoveChecker(Game game)
	{
		 return moveCheckerMap.get(game.getClass());
	}
}
