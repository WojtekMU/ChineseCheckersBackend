package TPLab4.ChineseCheckersBackend.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Room repository class.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
