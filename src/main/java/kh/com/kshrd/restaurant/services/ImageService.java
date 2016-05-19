package kh.com.kshrd.restaurant.services;

import java.util.List;

import kh.com.kshrd.restaurant.filters.ImageFilter;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.utilities.Pagination;

public interface ImageService {

	//TODO: TO FIND ALL IMAGE BY FILTER AND PAGINATION
	public List<Image> findAllImages(ImageFilter filter, Pagination pagination);
	
	//TODO: TO FIND A IMAGE BY ID 
	public Image findImageById(Long id);
	
	//TODO: TO REGISTER A NEW IMAGE
	public Boolean addNewImage(Image image);
	
	//public Boolean addManyImages(List<Image> images);
	
	//TODO: TO UPDATE AN EXIST IMAGE
	public Boolean updateExistImage(Image image);
	
	//TODO: TO DELETE AN EXIST IMAGE
	public Boolean deleteImage(Long id);
}
