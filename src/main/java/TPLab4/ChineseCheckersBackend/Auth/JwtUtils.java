package TPLab4.ChineseCheckersBackend.Auth;

import TPLab4.ChineseCheckersBackend.User.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Jwt utilities class
 */
@Component
public class JwtUtils {
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * Jwt secret
     */
    @Value("${chineseCheckers.jwtSecret}")
    private String jwtSecret;

    /**
     * Jwt expiration time
     */
    @Value("${chineseCheckers.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Jwt token generator.
     *
     * @param authentication Authentication
     * @return Jwt token
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Method for getting user name from token.
     *
     * @param token Jwt token
     * @return Username
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Method for validating jwt tokens.
     *
     * @param authToken Jwt token
     * @return True if token is valid, else false.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}