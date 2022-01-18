import TPLab4.ChineseCheckersBackend.Room.*;
import TPLab4.ChineseCheckersBackend.User.User;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Mock
    private User user;

    @Test
    public void createRoomTest() {
        Room room1 = roomService.createRoom(user);
        Mockito.when(roomService.createRoom(user)).thenReturn(room1);
        verify(roomRepository, times(1)).save(room1);
    }

    @Test
    public void createRoomUserAlreadyInRoomTest() {
        CantCreateRoomException thrown = Assertions.assertThrows(CantCreateRoomException.class, () -> {
            Room room1 = Mockito.mock(Room.class);
            roomService.createRoom(user);
            Set<Room> rooms = ImmutableSet.of(room1);
            Mockito.when(user.getRooms()).thenReturn(rooms);
            roomService.createRoom(user);
        });
    }

    @Test
    public void joinRoomTest() {
        Room room1 = Mockito.mock(Room.class);
        roomService.joinRoom(user, room1);
    }

    @Test
    public void joinRoomUserAlreadyInRoomTest() {
        CantJoinRoomException thrown = Assertions.assertThrows(CantJoinRoomException.class, () -> {
            Room room1 = Mockito.mock(Room.class);
            Set<Room> rooms = ImmutableSet.of(room1);
            Mockito.when(user.getRooms()).thenReturn(rooms);
            roomService.joinRoom(user, room1);
        });
    }

    @Test
    public void joinRoomRoomIsFullTest() {
        CantJoinRoomException thrown = Assertions.assertThrows(CantJoinRoomException.class, () -> {
            Room room1 = Mockito.mock(Room.class);
            User user1 = Mockito.mock(User.class);
            User user2 = Mockito.mock(User.class);
            User user3 = Mockito.mock(User.class);
            User user4 = Mockito.mock(User.class);
            User user5 = Mockito.mock(User.class);
            User user6 = Mockito.mock(User.class);

            List<User> usersInRoom = Arrays.asList(user1, user2, user3, user4, user5, user6);
            Mockito.when(room1.getUsers()).thenReturn(usersInRoom);
            roomService.joinRoom(user, room1);
        });
    }

    @Test
    public void joinRoomGameAlreadyStartedTest() {
        CantJoinRoomException thrown = Assertions.assertThrows(CantJoinRoomException.class, () -> {
            Room room1 = Mockito.mock(Room.class);

            Mockito.when(room1.getGameStarted()).thenReturn(true);
            roomService.joinRoom(user, room1);
        });
    }

    @Test
    public void leaveRoomTest() {
        Room room1 = Mockito.mock(Room.class);
        User user1 = Mockito.mock(User.class);
        roomService.joinRoom(user, room1);
        List<User> usersInRoom = new ArrayList<User>();
        usersInRoom.add(user);
        usersInRoom.add(user1);
        Mockito.when(room1.getUsers()).thenReturn(usersInRoom);
        Mockito.when(room1.getGameStarted()).thenReturn(false);
        roomService.leaveRoom(user, room1);
        verify(roomRepository, times(2)).save(room1);
    }

    @Test
    public void leaveRoomDeleteTest() {
        Room room1 = Mockito.mock(Room.class);
        roomService.joinRoom(user, room1);
        List<User> usersInRoom = new ArrayList<User>();
        usersInRoom.add(user);
        Mockito.when(room1.getUsers()).thenReturn(usersInRoom);
        Mockito.when(room1.getGameStarted()).thenReturn(false);
        roomService.leaveRoom(user, room1);
        verify(roomRepository, times(1)).delete(room1);
    }

    @Test
    public void leaveRoomNotInRoomTest() {
        AccessDeniedException thrown = Assertions.assertThrows(AccessDeniedException.class, () -> {
            Room room1 = Mockito.mock(Room.class);
            List<User> usersInRoom = new ArrayList<User>();
            Mockito.when(room1.getUsers()).thenReturn(usersInRoom);
            roomService.leaveRoom(user, room1);
        });
    }

    @Test
    public void leaveRoomGameAlreadyStartedTest() {
        CantLeaveRoomException thrown = Assertions.assertThrows(CantLeaveRoomException.class, () -> {
            Room room1 = Mockito.mock(Room.class);
            roomService.joinRoom(user, room1);
            List<User> usersInRoom = new ArrayList<User>();
            usersInRoom.add(user);
            Mockito.when(room1.getUsers()).thenReturn(usersInRoom);
            Mockito.when(room1.getGameStarted()).thenReturn(true);
            roomService.leaveRoom(user, room1);
        });
    }

    @Test
    public void loadRoomByIdTest() {
        Long roomId = 1L;
        Room room1 = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.of(room1));
        Room room2 = roomService.loadRoomById(roomId);
        Assertions.assertEquals(room1, room2);
    }

    @Test
    public void loadRoomByIdRoomNotFoundTest() {
        RoomNotFoundException thrown = Assertions.assertThrows(RoomNotFoundException.class, () -> {
            Long roomId = 1L;
            Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.empty());
            roomService.loadRoomById(roomId);
        });
    }
}