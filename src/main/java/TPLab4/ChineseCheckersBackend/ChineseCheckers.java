package TPLab4.ChineseCheckersBackend;

import TPLab4.ChineseCheckersBackend.Role.ERole;
import TPLab4.ChineseCheckersBackend.Role.Role;
import TPLab4.ChineseCheckersBackend.Role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import java.util.*;

/**
 * Main class of the application
 */
@SpringBootApplication
@EnableScheduling
public class ChineseCheckers 
{
	/**
	 * Spring application launcher
	 * @param args Starting arguments3
	 */
	public static void main(String[] args) 
	{
		SpringApplication.run(ChineseCheckers.class, args);
	}

	/**
	 * Method for adding roles to database
	 * @param userRepository User repository
	 * @param roleRepository Role repository
	 * @return CommandLineRunner
	 */
    @Bean
    public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
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