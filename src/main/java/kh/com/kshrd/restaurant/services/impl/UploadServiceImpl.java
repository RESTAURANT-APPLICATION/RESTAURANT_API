package kh.com.kshrd.restaurant.services.impl;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.services.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Override
	public List<Image> uploadMultipart(List<CommonsMultipartFile> files, HttpServletRequest request) {
		try{
			List<Image> imageFiles = new ArrayList<Image>();
			for(CommonsMultipartFile file: files){
				String filename = "";
				if (!file.isEmpty()) {
					Image image = new Image();
					String savePath= request.getSession().getServletContext().getRealPath("/resources/images");
					String savePathThumbnail = request.getSession().getServletContext().getRealPath("/resources/images/thumbnails");
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
		                
//		                long startTime = System.currentTimeMillis();
//		                File f = new File(getURLWithContextPath(request) + "/resources/images/" + filename);
//		                BufferedImage img = ImageIO.read(f); // load image
//
//		                //Quality indicate that the scaling implementation should do everything
//		                 // create as nice of a result as possible , other options like speed
//		                 // will return result as fast as possible
//		              //Automatic mode will calculate the resultant dimensions according
//		              //to image orientation .so resultant image may be size of 50*36.if you want
//		              //fixed size like 50*50 then use FIT_EXACT
//		              //other modes like FIT_TO_WIDTH..etc also available.
//
//		                BufferedImage thumbImg = Scalr.resize(img, Method.QUALITY,Mode.AUTOMATIC, 
//		                           50,
//		                                 50, Scalr.OP_ANTIALIAS);
//		                 //convert bufferedImage to outpurstream 
//		                ByteArrayOutputStream os = new ByteArrayOutputStream();
//		                ImageIO.write(thumbImg,"jpg",os);
//		                
//		                
//		                //or wrtite to a file
//		                
//		                File f2 = new File(getURLWithContextPath(request) + "/resources/images/" + randomUUIDFileName+"_thumb."+extension);
//		                
//		                
//		                ImageIO.write(thumbImg, "jpg", f2);
		                
//		                System.out.println("time is : " +(System.currentTimeMillis()-startTime));
		                
		                //ResizeImage.resizeImage( this.getClass().getResourceAsStream(savePath + File.separator  + filename), 600, 400, "png","testResizeImage",savePathThumbnail+File.separator);
		                
		                
		                /*java.awt.Image img = null;
		                BufferedImage tempJPG = null;
		                File newFileJPG = null;
		                
		                img = ImageIO.read(new File(savePath + File.separator  + filename));
		                tempJPG = resizeImage(img, 100, 100);
		                newFileJPG = new File(savePath + File.separator + randomUUIDFileName+"_thumb."+ extension);
		                ImageIO.write(tempJPG, "jpg", newFileJPG);
*/
		                
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
		}catch(

	Exception ex)

	{
			ex.printStackTrace();
		}return null;

	}

	public static String getURLWithContextPath(HttpServletRequest request) {
	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	public static BufferedImage resizeImage(final java.awt.Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        //graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
