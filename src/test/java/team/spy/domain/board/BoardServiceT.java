package team.spy.domain.board;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.JpaMappingTest;
import team.spy.domain.board.service.BoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceT extends JpaMappingTest{

	@Autowired
	BoardService boardService;
	
	@Test
	public void selectSpeficColumnsT()
	{
		// when
		boardService.findBoardView();
		
		// then
//		assertThat(board.getTitle(), is(boardName));
	}
	
}
