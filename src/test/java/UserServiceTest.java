import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;
import TPLab4.ChineseCheckersBackend.User.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void loadUserByUsernameTest() {
        String username = "user";
        User user1 = Mockito.mock(User.class);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        User user2 = userService.loadUserByUsername(username);
        Assertions.assertEquals(user1, user2);
    }

    @Test
    public void loadUserByUsernameUserNotFoundTest() {
        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            String username = "user";
            Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
            userService.loadUserByUsername(username);
        });
    }
}
