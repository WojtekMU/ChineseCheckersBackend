package TPLab4.ChineseCheckersBackend;

import TPLab4.ChineseCheckersBackend.Role.ERole;
import TPLab4.ChineseCheckersBackend.Role.Role;
import TPLab4.ChineseCheckersBackend.Role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import java.util.*;

/**
 * Main class of the application
 */
@SpringBootApplication
public class ChineseCheckers 
{
	/**
	 * Spring application launcher
	 * @param args Starting arguments
	 */
	public static void main(String[] args) 
	{
		SpringApplication.run(ChineseCheckers.class, args);
	}
	
    @Bean
    public CommandLineRunner init(RoleRepository roleRepository) {
        return (args) -> {
			if(!roleRepository.existsByName(ERole.ROLE_USER))
			{
				Role user = new Role(ERole.ROLE_USER);

				roleRepository.save(user);
			}

			if(!roleRepository.existsByName(ERole.ROLE_ADMIN))
			{
				Role admin = new Role(ERole.ROLE_ADMIN);

				roleRepository.save(admin);
			}
        };
    }
}