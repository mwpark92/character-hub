package team.spy.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {

	private int uploaded;
    private String fileName;
    private String url;
  
}
