import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserDetailsServiceImpl;
import TPLab4.ChineseCheckersBackend.User.UserRepository;
import TPLab4.ChineseCheckersBackend.User.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceImplTest
{
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    public void loadUserByUsernameTest()
    {
        String username  = "user";
        User user1 = Mockito.mock(User.class);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        UserDetails user2 = userDetailsServiceImpl.loadUserByUsername(username);
        Assertions.assertEquals(user1.getUsername(), user2.getUsername());
        Assertions.assertEquals(user1.getPassword(), user2.getPassword());
    }

    @Test
    public void loadUserByUsernameUserNotFoundTest()
    {
        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            String username  = "user";
            Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
            userDetailsServiceImpl.loadUserByUsername(username);
        });
    }
}
