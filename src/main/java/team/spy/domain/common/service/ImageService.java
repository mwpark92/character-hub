package team.spy.domain.common.service;

import org.springframework.stereotype.Service;

import team.spy.domain.common.process.ResourceUtils;
import team.spy.domain.common.properties.FileUploadProperties;

@Service
public class ImageService extends ResourceUtils{

	public ImageService(FileUploadProperties prop) {
		super(prop);
		// TODO Auto-generated constructor stub
	}
}
