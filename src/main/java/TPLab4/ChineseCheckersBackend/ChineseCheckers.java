package TPLab4.ChineseCheckersBackend;

import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.Game.StandardTwoPlayersGame;
import TPLab4.ChineseCheckersBackend.GameFactory.FourPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.SixPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.ThreePlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.TwoPlayerGameFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ChineseCheckers 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ChineseCheckers.class, args);
	}
	
    @Bean
    public CommandLineRunner init(UserRepository userRepository) {
        return (args) -> {
        	userRepository.save(new User("player1", new BCryptPasswordEncoder().encode("asdffdsa")));
        	userRepository.save(new User("player2",  new BCryptPasswordEncoder().encode("asdffdsa")));
        	userRepository.save(new User("player3", new BCryptPasswordEncoder().encode("asdffdsa")));
        	userRepository.save(new User("player4",  new BCryptPasswordEncoder().encode("asdffdsa")));
        	userRepository.save(new User("player5", new BCryptPasswordEncoder().encode("asdffdsa")));
        	userRepository.save(new User("player6",  new BCryptPasswordEncoder().encode("asdffdsa")));
        };
    }
}