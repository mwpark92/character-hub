package team.spy.domain.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.JpaMappingTest;
import team.spy.domain.board.service.CalendarBoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarBoardServiceT extends JpaMappingTest{

	@Autowired
	private CalendarBoardService calendarBoardService;
	
	@Test
	public void getBoardListT()
	{
		
	}
	
	
}
