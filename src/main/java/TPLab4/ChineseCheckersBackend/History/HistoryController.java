package TPLab4.ChineseCheckersBackend.History;

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

import java.security.Principal;

/**
 * REST controller for history.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers/history")
public class HistoryController {
    /**
     * History service
     */
    @Autowired
    private HistoryService historyService;

    /**
     * User service
     */
    @Autowired
    private UserService userService;

    /**
     * Method handing replays request.
     *
     * @param principal User sending the request.
     * @return User replays list or error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/replays", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReplays(Principal principal) {
        try {
            User user = userService.loadUserByUsername(principal.getName());

            return ResponseEntity.ok(historyService.getReplays(user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (HistoryNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Method for handling moves request.
     *
     * @param historyId History id
     * @param principal User sending the request.
     * @return Moves list or error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/moves", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMoves(@RequestParam Long historyId, Principal principal) {
        try {
            History history = historyService.loadHistoryById(historyId);
            User user = userService.loadUserByUsername(principal.getName());

            return ResponseEntity.ok(historyService.getMoves(history, user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (HistoryNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }

    /**
     * Method for handling replay board request.
     *
     * @param historyId History id
     * @param principal User sending the request.
     * @return Replay list or error message.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/replayBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReplayBoard(@RequestParam Long historyId, Principal principal) {
        try {
            History history = historyService.loadHistoryById(historyId);
            User user = userService.loadUserByUsername(principal.getName());

            return ResponseEntity.ok(historyService.getReplayBoard(history, user));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (HistoryNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
        }
    }
}
