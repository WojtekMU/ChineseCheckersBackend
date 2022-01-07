package TPLab4.ChineseCheckersBackend.Move;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import TPLab4.ChineseCheckersBackend.Tile.Tile;

@Entity
public class Move 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	private Long id;
	
    @OneToOne
    @JoinColumn(name = "tile_id")
    private Tile firstTile;
    
    @OneToOne
    @JoinColumn(name = "tile_id")
    private Tile secondTile;
}
