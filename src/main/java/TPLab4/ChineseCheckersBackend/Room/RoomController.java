package TPLab4.ChineseCheckersBackend.Room;


import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.LeaveRoomRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * REST controller for rooms
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers/room")
public class RoomController {
    /**
     * Room service
     */
    @Autowired
    private RoomService roomService;

    /**
     * User service
     */
    @Autowired
    private UserService userService;

    /**
     * Create room request handler
     *
     * @param principal User sending the request.
     * @return Server response with room id on success.
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewRoom(Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.createRoom(user);

            return ResponseEntity.ok(room.getId());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (CantCreateRoomException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Room list request handler.
     *
     * @return Room list JSON or error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/roomList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoomsToJoin() {
        return ResponseEntity.ok(roomService.loadRoomList());
    }

    /**
     * Player list request handler.
     *
     * @param roomId    Room id
     * @param principal User sending the request.
     * @return Player list JSON or error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/playerList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayersInRoom(@RequestParam Long roomId, Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.loadRoomById(roomId);

            return ResponseEntity.ok(roomService.getPlayersInRoom(room, user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Room join request handler
     *
     * @param joinRequest Join request
     * @param principal   User sending the request.
     * @return Server response
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/join")
    public ResponseEntity<?> joinGame(@Valid @RequestBody JoinRequest joinRequest, Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.loadRoomById(joinRequest.getRoomId());

            roomService.joinRoom(user, room);

            return ResponseEntity.ok(new MessageResponse("Successfully joined room!"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (CantJoinRoomException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Leave room request handler.
     *
     * @param leaveRoomRequest Leave room requeest
     * @param principal        User sending the request.
     * @return Server response.
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/leave")
    public ResponseEntity<?> leaveRoom(@Valid @RequestBody LeaveRoomRequest leaveRoomRequest, Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.loadRoomById(leaveRoomRequest.getRoomId());

            roomService.leaveRoom(user, room);

            return ResponseEntity.ok(new MessageResponse("Successfully left room!"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (CantLeaveRoomException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Game started request handler.
     *
     * @param roomId    Room id
     * @param principal User sending the request.
     * @return Boolean value representing whether a game has already started or an error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameStarted")
    public ResponseEntity<?> gameStarted(@RequestParam Long roomId, Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.loadRoomById(roomId);

            return ResponseEntity.ok(roomService.getGameStarted(room, user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Game id request response.
     *
     * @param roomId    Room id
     * @param principal User sending the request.
     * @return Linked game id or an error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameId")
    public ResponseEntity<?> getGameId(@RequestParam Long roomId, Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.loadRoomById(roomId);

            return ResponseEntity.ok(roomService.getGameId(room, user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Last update request handler.
     *
     * @param roomId    Room id
     * @param principal User sending the request.
     * @return Last update date or an error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/lastUpdate")
    public ResponseEntity<?> getLastUpdate(@RequestParam Long roomId, Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Room room = roomService.loadRoomById(roomId);

            return ResponseEntity.ok(roomService.getLastUpdate(room, user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (RoomNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }
}
