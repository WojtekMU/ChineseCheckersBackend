package TPLab4.ChineseCheckersBackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@SpringBootApplication
public class ChineseCheckers 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ChineseCheckers.class, args);
	}
	
    @Bean
    public CommandLineRunner createUsers(UserRepository userRepository) {
        return (args) -> {
        	userRepository.save(new User("player1", new BCryptPasswordEncoder().encode("asdffdsa")));
        	userRepository.save(new User("player2",  new BCryptPasswordEncoder().encode("asdffdsa")));
        };
    }
}