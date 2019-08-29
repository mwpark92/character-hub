package team.spy.domain.board.process;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

import team.spy.domain.common.process.ResourceUtils;

public class BoardImageProcess implements BoardProcess{

	private final ResourceUtils resourceUtils;
	
	public BoardImageProcess(ResourceUtils resourceUtils) {
		this.resourceUtils = resourceUtils;
	}
	
    public void generateThumbnail()
    {
    	// call the imageProcess
    	
    	// find first img
    	
    	// compress
    	
    	// generate thumbnail
    }
    
    public List<String> findImageFileName(String contents)
    {
    	return Jsoup.parseBodyFragment(contents).getElementsByTag("img").stream()
    			.map(img -> img.attr("src") )
    			.map(str -> str.split("/")[str.split("/").length - 1])
    			.collect(Collectors.toList());
    }
    
    public void bringFileFromTemp(Long id, String contents)
    {
    	final Path path = resourceUtils.getResourceRootDirectory().resolve("calendars/" + id).normalize();
    	List<String> imageFileNames = findImageFileName(contents);
    	imageFileNames.stream().map(fileName -> resourceUtils.moveResource(fileName, path));
    }
}
