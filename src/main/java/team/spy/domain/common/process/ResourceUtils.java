package team.spy.domain.common.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import team.spy.domain.common.exceptions.FileDownloadException;
import team.spy.domain.common.exceptions.FileUploadException;
import team.spy.domain.common.properties.FileUploadProperties;


/**
 * Common Code of relating Resource
 * @author kwanghoyeom
 *
 */
@Slf4j
public class ResourceUtils {

	private final Path fileLocation;
    
    public ResourceUtils(FileUploadProperties prop) 
    {
        fileLocation = Paths.get(prop.getImageUploadRootDirectory() + "/temp").toAbsolutePath().normalize();
        
        try 
        {
            Files.createDirectories(fileLocation);
        }catch(Exception e) 
        {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }
	
    public String saveResource(MultipartFile file) {
        
    	final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
            {
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            }
            
            final Path targetLocation = fileLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return fileName;
        }catch(Exception e) {
            throw new FileUploadException('['+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
        
    }
	
    
    public Resource loadFileAsResource(String fileName) {
        try {
            final Path filePath = fileLocation.resolve(fileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());

            if(filePath.toFile().exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }
    
    
	public void moveResource(String fileName, Path moveLocation)
    {
    	final Path filePath = fileLocation.resolve(fileName).normalize();
    	final Path movePath = fileLocation.resolve(moveLocation).resolve(fileName).normalize();
    	
    	log.info("currentPath : " + filePath);
    	log.info("movePath : " + movePath);

        try {
            copyResource(filePath, movePath);
            deleteResource(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyResource(Path filePath, Path copyPath) throws IOException {
        Files.createDirectories(copyPath);
        Files.copy(filePath, copyPath, StandardCopyOption.REPLACE_EXISTING);

    }

    public void deleteResource(Path filePath) throws IOException {
        Files.delete(filePath);
    }
	
	public Path getResourceRootDirectory()
	{
		return fileLocation;
	}
}
