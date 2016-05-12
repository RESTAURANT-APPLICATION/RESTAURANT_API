package kh.com.kshrd.restaurant.restcontrollers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
@RequestMapping(value="/v1/api/admin/upload")
public class RestUploadController {

	@RequestMapping(value="/single", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> uploadSingle(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request){
		System.out.println("FILE ==> "+file.getOriginalFilename());
		String filename = "";
        Map<String, Object> map = new HashMap<String, Object>();
		if (!file.isEmpty()) {
			String savePath= request.getSession().getServletContext().getRealPath("/resources/images");
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
        		map.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY");
        		map.put("CODE", "0000");
        		map.put("IMAGE_URL", savePath + File.separator + filename);
        		map.put("IMAGE_NAME", filename);
        		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
            } catch (Exception e) {
            	e.printStackTrace();
                System.out.println("MESSAGE ==> YOU FAILED TO UPLOAD " + filename + " => " + e.getMessage());
                map.put("MESSAGE", "ERROR " + e.getMessage());
        		map.put("CODE", "8888");
        		map.put("IMAGE_URL", savePath + File.separator + filename);
        		map.put("IMAGE_NAME", filename);
        		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            System.out.println("YOU FAILED TO UPLOAD " + filename + " BECAUSE THE FILE WAS EMPTY.");
    		map.put("MESSAGE", "UNSUCCESSFULLY");
    		map.put("CODE", "9999");
    		map.put("DATA", filename);
    		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
        }
	}
		
	@RequestMapping(value="/multiple", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> uploadMultiple(@RequestParam("files") CommonsMultipartFile[] files, HttpServletRequest request){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> mapFiles = new ArrayList<Map<String, Object>>();
			if(files.length<1){
				responseMap.put("MESSAGE", "FILE IS NOT FOUND...");
				responseMap.put("CODE", "8888");
				return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
			}
			for(CommonsMultipartFile file: files){
				String filename = "";
				if (!file.isEmpty()) {
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
		        		map.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY");
		        		map.put("CODE", "0000");
		        		map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + filename);
		        		map.put("IMAGE_NAME", filename);
		        		mapFiles.add(map);
		            } catch (Exception e) {
		                System.out.println("MESSAGE ==> YOU FAILED TO UPLOAD " + filename + " => " + e.getMessage());
		                map.put("MESSAGE", "ERROR " + e.getMessage());
		        		map.put("CODE", "7777");
		        		map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + filename);
		        		map.put("IMAGE_NAME", filename);
		        		mapFiles.add(map);
		            }
		        } else {
		            System.out.println("YOU FAILED TO UPLOAD " + filename + " BECAUSE THE FILE WAS EMPTY.");
		            responseMap.put("MESSAGE", "UNSUCCESSFULLY");
		            responseMap.put("CODE", "8888");
		            responseMap.put("DATA", filename);
		    		//return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
		        }
			}
			responseMap.put("IMAGES", mapFiles);
		}catch(Exception ex){
			responseMap.put("MESSAGE", "ERROR " + ex.getMessage());
			responseMap.put("CODE", "9999");
    		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
	}
	
	public static String getURLWithContextPath(HttpServletRequest request) {
	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	@RequestMapping(value="/base64single",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, Object>> uploadImage2(@RequestBody String strBase64,HttpServletRequest request)
    {
		Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            //This will decode the String which is encoded by using Base64 class
            byte[] imageByte=Base64.decodeBase64(strBase64);
            
            
            String savePath= request.getSession().getServletContext().getRealPath("/resources/images");

            UUID uuid = UUID.randomUUID();
            String randomUUIDFileName = uuid.toString();
            
            String directory=request.getServletContext().getRealPath("/resources/images/")+randomUUIDFileName + ".jpg";

            new FileOutputStream(directory).write(imageByte);
            
            map.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY");
    		map.put("CODE", "0000");
    		map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + randomUUIDFileName + ".jpg");
    		map.put("IMAGE_NAME", randomUUIDFileName + ".jpg");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        catch(Exception e)
        {
        	map.put("MESSAGE", "YOU HAVE BEEN FAILED WHEN UPLOAD THE IMAGE");
    		map.put("CODE", "9999");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }

    }
	
}