package kh.com.kshrd.restaurant.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.services.UploadService;

@Service
public class UploadServiceImpl implements UploadService{

	@Override
	public List<Image> uploadMultipart(List<CommonsMultipartFile> files, HttpServletRequest request) {
		try{
			List<Image> imageFiles = new ArrayList<Image>();
			for(CommonsMultipartFile file: files){
				String filename = "";
				if (!file.isEmpty()) {
					Image image = new Image();
					String savePath= request.getSession().getServletContext().getRealPath("/resources/images");
					Map<String, Object> map = new HashMap<String, Object>();
		            try { 
		            	filename = file.getOriginalFilename();
		                byte[] bytes = file.getBytes();
		                UUID uuid = UUID.randomUUID();
		                String randomUUIDFileName = uuid.toString();
		                String extension = filename.substring(filename.lastIndexOf(".")+1);
						System.out.println(savePath);
						File path = new File(savePath);
						if(!path.exists()){
							path.mkdirs();
						} 
						filename = randomUUIDFileName + "." + extension;
		                BufferedOutputStream stream = new BufferedOutputStream(
		                									new FileOutputStream(
		                											new File(savePath + File.separator  + filename)));
		                stream.write(bytes);
		                stream.close();
		                
		                System.out.println("MESSAGE ==> YOU HAVE BEEN UPLOADED " + savePath + File.separator + filename + " SUCCESSFULLY!");
		                image.setTitle(filename);
		        		image.setUrl(getURLWithContextPath(request) + "/resources/images/" + filename);
		        		imageFiles.add(image);
		            } catch (Exception e) {
		                System.out.println("MESSAGE ==> YOU FAILED TO UPLOAD " + filename + " => " + e.getMessage());
		                map.put("MESSAGE", "ERROR " + e.getMessage());
		        		map.put("CODE", "7777");
		            }
		        } else {
		            System.out.println("YOU FAILED TO UPLOAD BECAUSE THE FILE WAS EMPTY.");
		        }
			}
			return imageFiles;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String getURLWithContextPath(HttpServletRequest request) {
		   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}

}
