package TPLab4.ChineseCheckersBackend.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Role repository class
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    /**
     * Method for finding roles by name.
     * @param name Role name
     * @return Optional of role
     */
    Optional<Role> findByName(ERole name);

    /**
     * Method for checking whether role exists.
     * @param name Role name
     * @return True when role exists, else false.
     */
    Boolean existsByName(ERole name);
}