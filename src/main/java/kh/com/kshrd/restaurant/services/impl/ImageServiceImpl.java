package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.com.kshrd.restaurant.filters.ImageFilter;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.repositories.ImageRepository;
import kh.com.kshrd.restaurant.services.ImageService;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Service
public class ImageServiceImpl implements ImageService{

	private ImageRepository imageRepository;
	
	@Override
	public List<Image> findAllImages(ImageFilter filter, Pagination pagination) {
		try{
			return imageRepository.findAllImages(filter, pagination);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Image findImageById(Long id) {
		try{
			return imageRepository.findImageById(id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return  null;
	}

	@Override
	public Boolean addNewImage(Image image) {
		try{
			if(imageRepository.save(image)>0){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean updateExistImage(Image image) {
		return null;
	}

	@Override
	public Boolean deleteImage(Long id) {
		return null;
	}
	
	/*@Override
	public Boolean addManyImages(List<Image> images) {
		try{
			int results[] = imageRepository.save(images);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}*/

}
