package team.spy.domain;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.User.dto.User;
import team.spy.domain.User.repository.UserRepository;
import team.spy.domain.board.dto.CommonBoard;
import team.spy.domain.board.repository.CommonBoardRepository;
import team.spy.domain.enums.BoardType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaMappingTest {

	private final String boardName = "Test";
	private final String email = "test@gmail.com";
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommonBoardRepository boardRepository;

	
	@Before
	public void init()
	{
		User user = userRepository.save(User.builder()
				.nickname("ykh")
				.password("ykh")
				.email(email)
				.createDate(LocalDateTime.now())
				.build());
		
		boardRepository.save(CommonBoard.builder()
				.title(boardName)
				.content("contents")
				.boardType(BoardType.FREE)
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.user(user)
				.build());
	}
}
