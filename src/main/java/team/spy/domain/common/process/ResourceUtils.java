package team.spy.domain.common.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
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
        this.fileLocation = Paths.get(prop.getImageUploadRootDirectory() + "/temp").toAbsolutePath().normalize();
        
        try 
        {
            Files.createDirectories(this.fileLocation);
        }catch(Exception e) 
        {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }
	
    public String saveResource(MultipartFile file) {
        
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            
            Path targetLocation = this.fileLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return fileName;
        }catch(Exception e) {
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
        
    }
	
    
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }
    
    
	public boolean moveResource(String fileName, Path moveLocation)
    {
    	Path filePath = this.fileLocation.resolve(fileName).normalize();
    	Path movePath = this.fileLocation.resolve(moveLocation).resolve(fileName).normalize();
    	
    	log.info("filePath : " + filePath.toString());
    	log.info("movePath : " + movePath.toString());
    	
    	try {
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists())
			{
				Files.createDirectories(movePath);
				Files.copy(resource.getInputStream(), movePath, StandardCopyOption.REPLACE_EXISTING);
				return resource.getFile().delete();
			}
			else 
			{
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	
	public Path getResourceRootDirectory()
	{
		return this.fileLocation;
	}
}
