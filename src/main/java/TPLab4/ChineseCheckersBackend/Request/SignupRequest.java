package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Sign up request class
 */
public class SignupRequest {
    /**
     * User username
     */
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    /**
     * User password hash
     */
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    /**
     * User roles
     */
    private Set<String> role;

    /**
     * User username getter.
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * User username setter.
     *
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * USer password getter.
     *
     * @return User password
     */
    public String getPassword() {
        return password;
    }

    /**
     * User password setter.
     *
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * User roles getter.
     *
     * @return User roles
     */
    public Set<String> getRole() {
        return this.role;
    }

    /**
     * User roles setter.
     *
     * @param role Role set
     */
    public void setRole(Set<String> role) {
        this.role = role;
    }
}