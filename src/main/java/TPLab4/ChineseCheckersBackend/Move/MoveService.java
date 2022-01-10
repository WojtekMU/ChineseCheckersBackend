package TPLab4.ChineseCheckersBackend.Move;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public void saveMove(User player, History history, Tile firstTile, Tile secondTile)
	{
		Move move = new Move();
		
		move.setPlayer(player);
		move.setHistory(history);
		move.setFirstTile(firstTile);
		move.setSecondTile(secondTile);
		
		moveRepository.save(move);
	}
}
