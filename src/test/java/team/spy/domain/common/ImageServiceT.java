package team.spy.domain.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import team.spy.domain.common.service.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceT {

	@Autowired
	ImageService imageService;
	
	@Value("${file.image-upload-root-directory}")
	String rootDirectory;
	
	@Test
	public void moveNdeleteImageAsResourceT()
	{
		// given
		String fileName = "인천 물류센터 출장 교통비.jpeg";
		Path movePath = Paths.get(rootDirectory + "/boards/calendars/" + 10L).toAbsolutePath().normalize();;
		
		System.out.println("path : " + movePath);
		
		// when
		imageService.moveResource(fileName, movePath);
	}
	
	
	
}
