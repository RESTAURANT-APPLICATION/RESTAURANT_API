package kh.com.kshrd.restaurant.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kh.com.kshrd.restaurant.models.Image;

public interface UploadService {
	
	public List<Image> uploadMultipart(List<CommonsMultipartFile> files, HttpServletRequest request);

}
