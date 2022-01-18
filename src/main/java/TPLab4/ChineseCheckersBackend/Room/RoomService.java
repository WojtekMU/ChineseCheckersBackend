package TPLab4.ChineseCheckersBackend.Room;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Room service class.
 */
@Service
@Transactional
public class RoomService {
    /**
     * Room repository
     */
    @Autowired
    private RoomRepository roomRepository;

    /**
     * Method validating whether user has access to an element.
     *
     * @param room Room
     * @param user User
     * @throws AccessDeniedException If the user does not have access.
     */
    private void validate(Room room, User user) throws AccessDeniedException {
        if (!room.getUsers().contains(user)) {
            throw new AccessDeniedException("User does not belong to this room!");
        }
    }

    /**
     * Method loading room by id.
     *
     * @param roomId Room id
     * @return Room
     * @throws RoomNotFoundException When room was not found.
     */
    public Room loadRoomById(Long roomId) throws RoomNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room does not exist!"));

        return room;
    }

    /**
     * Method returning players in room.
     *
     * @param room Room
     * @param user User requesting data
     * @return Player in room list
     * @throws AccessDeniedException When user is not allowed to get this data.
     */
    public List<User> getPlayersInRoom(Room room, User user) throws AccessDeniedException {
        validate(room, user);

        return room.getUsers();
    }

    /**
     * Method returning game started boolean.
     *
     * @param room Room
     * @param user Game started boolean value
     * @return Player in room list
     * @throws AccessDeniedException When user is not allowed to get this data.
     */
    public String getGameStarted(Room room, User user) throws AccessDeniedException {
        validate(room, user);

        return room.isGameStarted().toString();
    }

    /**
     * Method returning game id.
     *
     * @param room Room
     * @param user User requesting data
     * @return Game id
     * @throws AccessDeniedException When user is not allowed to get this data.
     */
    public Long getGameId(Room room, User user) throws AccessDeniedException {
        validate(room, user);

        return room.getGame().getId();
    }

    /**
     * Method returning last update time in room.
     *
     * @param room Room
     * @param user User requesting data
     * @return Last update time
     * @throws AccessDeniedException When user is not allowed to get this data.
     */
    public Date getLastUpdate(Room room, User user) throws AccessDeniedException {
        validate(room, user);

        return room.getLastUpdate();
    }

    /**
     * Method returning room list.
     *
     * @return Room list.
     */
    public List<Room> loadRoomList() {
        return roomRepository.findAll();
    }

    /**
     * Method for creating a new room.
     *
     * @param user User requesting the creation.
     * @return New room
     * @throws AccessDeniedException   When user is not allowed to create a room.
     * @throws CantCreateRoomException When room can not be created.
     */
    public Room createRoom(User user) throws AccessDeniedException, CantCreateRoomException {
        if (user.getRooms().size() != 0) {
            throw new CantCreateRoomException("You cannot be in more than one room!");
        }

        Room room = new Room();
        room.getUsers().add(user);
        room.setGameStarted(false);

        roomRepository.save(room);

        return room;
    }

    /**
     * Method for joining a room.
     *
     * @param user User wanting to join
     * @param room Room
     * @throws CantJoinRoomException When user is not allowed to join.
     */
    public void joinRoom(User user, Room room) throws CantJoinRoomException {
        if (user.getRooms().size() != 0) {
            throw new CantJoinRoomException("You cannot be in more than one room!");
        }

        if (room.getGameStarted().equals(true)) {
            throw new CantJoinRoomException("Game has already started in this room!");
        }

        if (room.getUsers().size() == 6) {
            throw new CantJoinRoomException("Room is full!");
        }

        room.getUsers().add(user);
        room.setLastUpdate(new Date());

        roomRepository.save(room);
    }

    /**
     * Method for leaving a room.
     *
     * @param user User wanting to leave
     * @param room Room
     * @throws CantJoinRoomException When user is not allowed to leave.
     */
    public void leaveRoom(User user, Room room) throws AccessDeniedException, CantLeaveRoomException {
        validate(room, user);

        if (room.getGameStarted().equals(true)) {
            throw new CantLeaveRoomException("Cannot leave room while in game!");
        }

        room.getUsers().remove(user);
        room.setLastUpdate(new Date());

        if (room.getUsers().size() == 0) {
            roomRepository.delete(room);
        } else {
            roomRepository.save(room);
        }
    }


    /**
     * Method for setting the game started boolean value.
     *
     * @param room Room
     * @param game Game
     */
    public void setGameStarted(Room room, Game game) {
        room.setGameStarted(true);
        room.setGame(game);

        roomRepository.save(room);
    }

    /**
     * Method for detaching the game from the room.
     *
     * @param room Room
     */
    public void detachGame(Room room) {
        room.setGame(null);
        room.setGameStarted(false);

        roomRepository.save(room);
    }
}
