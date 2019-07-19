package team.pys;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import team.pys.domain.User.User;
import team.pys.domain.User.UserRepository;
import team.pys.domain.board.Board;
import team.pys.domain.board.BoardRepository;
import team.pys.domain.enums.BoardType;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception
	{
		return (args) -> {
			User user = userRepository.save(User.builder()
					.name("ykh")
					.password("ykh")
					.email("ykh@gmail.com")
					.createDate(LocalDateTime.now())
					.build());
			
			IntStream.rangeClosed(1, 200).forEach(index ->
					boardRepository.save(Board.builder()
							.title("title" + index)
							.subTitle("sub" + index)
							.content("contents")
							.boardType(BoardType.FREE)
							.createTime(LocalDateTime.now())
							.updateDate(LocalDateTime.now())
							.user(user).build()
							));
		};
	}

}
