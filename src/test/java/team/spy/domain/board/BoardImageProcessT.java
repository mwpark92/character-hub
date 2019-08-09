package team.spy.domain.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.board.process.BoardImageProcess;
import team.spy.domain.common.service.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardImageProcessT {

	BoardImageProcess boardImageProcess;
	String content;

	@Before
	public void init()
	{

		ImageService imageService = mock(ImageService.class);
		boardImageProcess = new BoardImageProcess(imageService);

		Path path = Paths.get("static/images/temp").toAbsolutePath().normalize();
		when(imageService.getResourceRootDirectory()).thenReturn(path);
		
		content = "<button id=\"kakao\" type=\"button\">KAKAO</button>\n" + 
				"	<button id=\"facebook\" type=\"button\">FACEBOOK</button>\n" + 
				"	<button id=\"google\" type=\"button\">GOOGLE</button>\n" + 
				"\n" + 
				"	<br>\n" + 
				"	\n" + 
				"	<img src = \"http://localhost:8080/downloadFile/스크린샷%202019-08-07%20오전%2010.20.07.png\"/>\n" + 
				"	";
	}
	
	@Test
	public void findImageFileNameT()
	{
		// when
		List<String> list = boardImageProcess.findImageFileName(content);
		
		// then
		assertThat(list.get(0), is("스크린샷%202019-08-07%20오전%2010.20.07.png"));
	}
	
	@Test
	public void bringFileFromTempT()
	{
		// given
		Long id = 10L;
				
		// when
		boardImageProcess.bringFileFromTemp(id, content);
	}
}
