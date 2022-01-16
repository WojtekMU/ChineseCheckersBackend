package TPLab4.ChineseCheckersBackend.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository class
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    /**
     * Method which finds user by username.
     * @param username Username
     * @return Optional of User
     */
	Optional<User> findByUsername(String username);

    /**
     * Method which checks whether user exists by username.
     * @param username Username
     * @return True if user exists, else false.
     */
    Boolean existsByUsername(String username);
}