package team.spy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import team.spy.domain.User.dto.User;
import team.spy.domain.User.repository.UserRepository;
import team.spy.domain.User.resolve.UserArgumentResolver;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.entity.Calendar;
import team.spy.domain.board.repository.BoardRepository;
import team.spy.domain.common.properties.ImageUploadProperties;
import team.spy.domain.enums.BoardType;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties({
    ImageUploadProperties.class
})
public class Application implements WebMvcConfigurer{

	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
		// ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
	}
	
	@Autowired
	private UserArgumentResolver userArgumentResolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}
	
	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception
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
			
			IntStream.rangeClosed(1, 200).forEach(index -> {
			
			Board board = Board.builder()
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
