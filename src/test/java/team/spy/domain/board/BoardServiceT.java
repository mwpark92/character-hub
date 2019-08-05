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
import team.spy.domain.enums.BoardType;

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
		Board createBoard = Board.builder()
						.user(user.getIdx())
						.title("CRUDBoardTest")
						.content("Hello")
						.boardType(BoardType.NOTICE)
						.createDate(LocalDateTime.now())
						.updateDate(null)
						.build();
		
		createBoard.setCalendar(new Calendar(null, null, 
				LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)),
				LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY))));
		
		boardService.generateBoard(createBoard);
		
		// Read All List
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageNumber()).thenReturn(1);
		when(pageable.getPageSize()).thenReturn(300);
		
		List<BoardSummary> boardList = boardService.findBoardList(pageable);
		
		assertThat(boardList.get(201).getBoardTitle(), is("CRUDBoardTest"));
		
		// Read One
		Board readBoard = boardService.readBoardByBoardIdx(202L);
		
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
