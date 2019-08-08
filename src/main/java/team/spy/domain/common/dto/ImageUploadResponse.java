package team.spy.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageUploadResponse {

	private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
