package team.spy.domain.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class ImageUploadProperties {

	private String uploadRootDirectory;
	
}
