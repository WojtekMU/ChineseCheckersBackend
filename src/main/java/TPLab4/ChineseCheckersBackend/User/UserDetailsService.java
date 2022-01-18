package TPLab4.ChineseCheckersBackend.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * User service class
 */
public interface UserDetailsService {
    /**
     * Method which loads user by username.
     *
     * @param username Username
     * @return UserDetails object
     * @throws UsernameNotFoundException When user is not found.
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}