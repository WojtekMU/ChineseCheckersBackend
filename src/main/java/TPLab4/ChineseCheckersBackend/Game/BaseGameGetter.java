package TPLab4.ChineseCheckersBackend.Game;

import TPLab4.ChineseCheckersBackend.GameFactory.FourPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.SixPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.ThreePlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.TwoPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaseGameGetter
{
    @Autowired
    private TwoPlayerGameFactory twoPlayerGameFactory;

    @Autowired
    private ThreePlayerGameFactory threePlayerGameFactory;

    @Autowired
    private FourPlayerGameFactory fourPlayerGameFactory;

    @Autowired
    private SixPlayerGameFactory sixPlayerGameFactory;

    @Autowired
    private GameService gameService;

    private static Map<Class<? extends Game>, Long> baseGameMap = new HashMap<Class<? extends Game>, Long>();

    @PostConstruct
    private void initMap()
    {
        StandardTwoPlayersGame standardTwoPlayersGameBase = twoPlayerGameFactory.createGame(new ArrayList<User>());
        gameService.setStatus(standardTwoPlayersGameBase, GameStatus.BASE);

        StandardThreePlayersGame standardThreePlayersGameBase = threePlayerGameFactory.createGame(new ArrayList<User>());
        gameService.setStatus(standardThreePlayersGameBase, GameStatus.BASE);

        StandardFourPlayersGame standardFourPlayersGameBase = fourPlayerGameFactory.createGame(new ArrayList<User>());
        gameService.setStatus(standardFourPlayersGameBase, GameStatus.BASE);

        StandardSixPlayersGame standardSixPlayersGameBase = sixPlayerGameFactory.createGame(new ArrayList<User>());
        gameService.setStatus(standardSixPlayersGameBase, GameStatus.BASE);

        baseGameMap.put(StandardTwoPlayersGame.class, standardTwoPlayersGameBase.getId());
        baseGameMap.put(StandardThreePlayersGame.class, standardThreePlayersGameBase.getId());
        baseGameMap.put(StandardFourPlayersGame.class, standardFourPlayersGameBase.getId());
        baseGameMap.put(StandardSixPlayersGame.class, standardSixPlayersGameBase.getId());
    }

    public Long getBaseGame(Game game)
    {
        return baseGameMap.get(game.getClass());
    }
}
