package team.spy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import team.spy.domain.User.dto.User;
import team.spy.domain.User.repository.UserRepository;
import team.spy.domain.User.resolve.UserArgumentResolver;
import team.spy.domain.board.dto.CommonBoard;
import team.spy.domain.board.repository.CommonBoardRepository;
import team.spy.domain.common.dto.Count;
import team.spy.domain.common.dto.CountProjectId;
import team.spy.domain.enums.BoardType;

@SpringBootApplication
public class Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	private UserArgumentResolver userArgumentResolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}
	
	@Bean
	public CommandLineRunner runner(UserRepository userRepository, CommonBoardRepository boardRepository) throws Exception
	{
		// 이렇게 구현된 Method는 return value에서lambda 식도 가능!
//		new CommandLineRunner() {
//			
//			@Override
//			public void run(String... args) throws Exception {
//				// TODO Auto-generated method stub
//				
//			}
//		};
		
		
		return (args) -> {
			
			User user = userRepository.save(User.builder()
					.nickname("ykh")
					.password("ykh")
					.email("ykh@gmail.com")
					.createDate(LocalDateTime.now())
					.build());
			
			
			
			IntStream.rangeClosed(1, 200).forEach(index ->
					boardRepository.save(CommonBoard.builder()
							.title("title" + index)
							.content("contents")
							.boardType(BoardType.FREE)
							.createDate(LocalDateTime.now())
							.updateDate(LocalDateTime.now())
							.user(user)
							.build()
							));
		};
	}
	
	

}
