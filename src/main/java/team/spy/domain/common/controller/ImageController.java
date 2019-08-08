package team.spy.domain.common.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import team.spy.domain.common.dto.ImageUploadResponse;
import team.spy.domain.common.service.ImageService;

@RestController
public class ImageController {

private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    
    @Autowired
    private ImageService service;
    
    private HttpHeaders httpHeaders;
	
	public ImageController()
	{
		httpHeaders = new HttpHeaders();
		httpHeaders.set("Cache-Control", "max-age=3600");
	}
    
    @PostMapping("/uploadFile")
    public ImageUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = service.storeFile(file);
        
        System.out.println(fileName);
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/downloadFile/")
                                .path(fileName)
                                .toUriString();
        
        return new ImageUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<ImageUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
         // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);
 
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        httpHeaders.add("ETag",  String.valueOf(resource.hashCode()));
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .headers(httpHeaders)
                .body(resource);
    }

	
}
