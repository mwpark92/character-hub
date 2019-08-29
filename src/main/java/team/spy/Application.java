package team.spy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import team.spy.domain.User.entity.User;
import team.spy.domain.User.repository.UserRepository;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.entity.Calendar;
import team.spy.domain.board.repository.BoardRepository;
import team.spy.domain.common.properties.FileUploadProperties;
import team.spy.domain.enums.BoardType;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(FileUploadProperties.class)
public class Application{

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository)
	{
		return args -> {
			final User user = userRepository.save(User.builder()
					.nickname("ykh")
					.password("ykh")
					.email("ykh@gmail.com")
					.createDate(LocalDateTime.now())
					.build());

			IntStream.rangeClosed(1, 200).forEach(index -> {

				final Board board = Board.builder()
						.title("title" + index)
						.content("contents")
						.boardType(BoardType.FREE)
						.createDate(LocalDateTime.now())
						.updateDate(LocalDateTime.now())
						.user(user.getIdx())
						.build();

				board.setCalendar(new Calendar("index : " + index, "address", LocalDate.now(), LocalDate.now()));
				boardRepository.save(board);
			});
		};
	}



}
