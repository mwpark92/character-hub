package team.spy.domain.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.common.service.CountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountServiceT {

	@Autowired
	private CountService countService;
	
	@Test
	public void countServiceT()
	{
		// when
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		countService.increaseCount(1, 1);
		
		// then
		assertThat(countService.getCount(1, 1), is(10));
		assertThat(countService.getCount(1, 1), is(10));
		assertThat(countService.getCount(1, 1), is(10));
	}
}
