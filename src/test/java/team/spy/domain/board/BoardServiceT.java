package team.spy.domain.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.JpaMappingTest;
import team.spy.domain.board.dto.BoardSummary;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.entity.Calendar;
import team.spy.domain.board.service.BoardService;
import team.spy.enums.BoardType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceT extends JpaMappingTest{

	@Autowired
	BoardService boardService;
	
	@Test
	public void findBoardListT()
	{
		// given
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageNumber()).thenReturn(1);
		when(pageable.getPageSize()).thenReturn(10);
		
		// when
		List<BoardSummary> boardList = boardService.findBoardList(pageable);
		
		// then
		assertThat(boardList, is(notNullValue()));
	}
	
	@Test
	public void findBoardByIdxT()
	{
		// given
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageNumber()).thenReturn(1);
		when(pageable.getPageSize()).thenReturn(10);
		
		// when
		BoardSummary board = boardService.findBoardByIdx(201L);
		
		// then
		assertThat(board, is(notNullValue()));
		assertThat(board.getBoardTitle(), is("Test"));
	}
	
	@Test
	public void CRUDBoardT()
	{
		// Create

		final Calendar board = new Calendar();
		board.setTitle("title");
		board.setContent("contents");
		board.setBoardType(BoardType.CALENDAR);
		board.setCreateDate(LocalDateTime.now());
		board.setUpdateDate(LocalDateTime.now());
		board.setUser(user.getIdx());
		board.setHomepage("index");
		board.setAddress("address");
		board.setStartDate(LocalDate.now());
		board.setEndDate(LocalDate.now().plusDays(5));

		boardService.generateBoard(board);
		
		// Read All List
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageNumber()).thenReturn(1);
		when(pageable.getPageSize()).thenReturn(300);
		
		List<BoardSummary> boardList = boardService.findBoardList(pageable);
		
		assertThat(boardList.get(201).getBoardTitle(), is("CRUDBoardTest"));
		
		// Read One
		Calendar readBoard = boardService.readBoardByBoardIdx(202L);
		
		assertThat(readBoard.getTitle(), is("CRUDBoardTest"));
		
		// Update
		readBoard.setTitle("Update");
		boardService.updateBoard(readBoard);
		
		Board updateBoard = boardService.readBoardByBoardIdx(202L);
		assertThat(updateBoard.getTitle(), is("Update"));
		
		// Delete
		boardService.deleteBoard(202L);
		
		Board deleteBoard = boardService.readBoardByBoardIdx(202L);
		
		assertThat(deleteBoard, is(nullValue()));
	}
	
}
