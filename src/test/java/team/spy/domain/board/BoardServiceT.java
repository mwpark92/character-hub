package team.spy.domain.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.JpaMappingTest;
import team.spy.domain.board.dto.CommonBoard;
import team.spy.domain.board.service.CommonBoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceT extends JpaMappingTest{

	@Autowired
	CommonBoardService boardService;
	
	@Test
	public void selectSpeficColumnsT()
	{
		// given
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageNumber()).thenReturn(1);
		when(pageable.getPageSize()).thenReturn(10);
		
		// when
		List<CommonBoard> boardList = boardService.findBoardList(pageable);
		
		// then
		assertThat(boardList, is(notNullValue()));
		assertThat(boardList.get(0).getUser().getNickname(), is("ykh"));
	}
	
}
