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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import team.spy.domain.common.dto.FileUploadResponse;
import team.spy.domain.common.process.ResourceUtils;
import team.spy.domain.common.service.ImageService;

@Slf4j
@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    @Autowired
    private ImageService service;
    
    private HttpHeaders httpHeaders;
	
	public ImageController()
	{
		httpHeaders = new HttpHeaders();
		httpHeaders.set("Cache-Control", "max-age=3600");
	}
    
	@PostMapping(value = "/upload")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = service.saveResource(file);
        String fileDownloadUri = "/downloadFile/" + fileName;
        return new FileUploadResponse(1, fileName, fileDownloadUri);
    }
    
    @PostMapping(value = "")
    public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
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
            log.info("Could not determine file type.");
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        httpHeaders.add("Content-Type", MediaType.parseMediaType(contentType).toString());
        httpHeaders.add("ETag",  String.valueOf(resource.hashCode()));
        
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

	
}
