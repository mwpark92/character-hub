package team.spy.domain.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {
	private String imageUploadRootDirectory;
	private String docUploadRootDirectory;
}
