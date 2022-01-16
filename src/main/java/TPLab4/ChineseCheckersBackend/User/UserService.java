package TPLab4.ChineseCheckersBackend.User;

import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service class
 */
@Service
@Transactional
public class UserService
{
    /**
     * User repository
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Method which loads user by username
     * @param username Username
     * @return User
     * @throws UsernameNotFoundException When user was not found.
     */
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));

        return user;
    }
}
