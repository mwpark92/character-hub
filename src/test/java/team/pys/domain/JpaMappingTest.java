package team.pys.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.pys.domain.User.User;
import team.pys.domain.User.UserRepository;
import team.pys.domain.board.Board;
import team.pys.domain.board.BoardRepository;
import team.pys.domain.enums.BoardType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {

	private final String boardName = "Test";
	private final String email = "test@gmail.com";
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Before
	public void init()
	{
		User user = userRepository.save(User.builder()
				.name("ykh")
				.password("ykh")
				.email(email)
				.createDate(LocalDateTime.now())
				.build());
		
		boardRepository.save(Board.builder()
				.title(boardName)
				.subTitle("sub")
				.content("contents")
				.boardType(BoardType.FREE)
				.createTime(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.user(user)
				.build());
	}
	
	@Test
	public void test()
	{
		User user = userRepository.findByEmail(email);
		assertThat(user.getName(), is("ykh"));
		assertThat(user.getPassword(), is("ykh"));
		
		Board board = boardRepository.findByUser(user);
		assertThat(board.getTitle(), is(boardName));
		
	}
	
}
