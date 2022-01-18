package TPLab4.ChineseCheckersBackend.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User details class.
 */
public class UserDetailsImpl implements UserDetails {
    /**
     * Serialization id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * User id.
     */
    private final Long id;

    /**
     * User username.
     */
    private final String username;

    /**
     * User hashed password.
     */
    @JsonIgnore
    private final String password;

    /**
     * User authorities.
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * User details constructor.
     *
     * @param id          Id
     * @param username    Username
     * @param password    Password
     * @param authorities User authorities set
     */
    public UserDetailsImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Method building user details.
     *
     * @param user User
     * @return UserDetailsImpl object
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    /**
     * User id getter.
     *
     * @return Id
     */
    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDetailsImpl user = (UserDetailsImpl) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
