package TPLab4.ChineseCheckersBackend;

import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.Game.StandardTwoPlayersGame;
import TPLab4.ChineseCheckersBackend.GameFactory.FourPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.SixPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.ThreePlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.TwoPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.Role.ERole;
import TPLab4.ChineseCheckersBackend.Role.Role;
import TPLab4.ChineseCheckersBackend.Role.RoleRepository;
import org.assertj.core.util.Sets;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import java.sql.Array;
import java.util.*;

@SpringBootApplication
public class ChineseCheckers 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ChineseCheckers.class, args);
	}
	
    @Bean
    public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {
			Role user = new Role(ERole.ROLE_USER);
			Role admin = new Role(ERole.ROLE_ADMIN);

			roleRepository.save(user);
			roleRepository.save(admin);

			User adm = new User("admin", new BCryptPasswordEncoder().encode("asdffdsa"));
			Set<Role> roles = new HashSet<Role>();
			roles.add(user);
			roles.add(admin);
			adm.setRoles(roles);
			userRepository.save(adm);

			User player1 = new User("player1", new BCryptPasswordEncoder().encode("asdffdsa"));
			player1.setRoles(Collections.singleton(user));
        	userRepository.save(player1);

			User player2 = new User("player2", new BCryptPasswordEncoder().encode("asdffdsa"));
			player2.setRoles(Collections.singleton(user));
			userRepository.save(player2);

			User player3 = new User("player3", new BCryptPasswordEncoder().encode("asdffdsa"));
			player3.setRoles(Collections.singleton(user));
			userRepository.save(player3);

			User player4 = new User("player4", new BCryptPasswordEncoder().encode("asdffdsa"));
			player4.setRoles(Collections.singleton(user));
			userRepository.save(player4);

			User player5 = new User("player5", new BCryptPasswordEncoder().encode("asdffdsa"));
			player5.setRoles(Collections.singleton(user));
			userRepository.save(player5);

			User player6 = new User("player6", new BCryptPasswordEncoder().encode("asdffdsa"));
			player6.setRoles(Collections.singleton(user));
			userRepository.save(player6);
        };
    }
}