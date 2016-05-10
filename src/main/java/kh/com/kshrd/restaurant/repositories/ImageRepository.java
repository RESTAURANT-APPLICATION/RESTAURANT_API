package kh.com.kshrd.restaurant.repositories;

import java.util.List;

import kh.com.kshrd.restaurant.filters.ImageFilter;
import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.utilities.Pagination;

public interface ImageRepository {

	public Long save(Image image);
	public Long save(List<Image> images);
	public List<Image> findAllImages(ImageFilter filter, Pagination pagination);
	public List<Image> findAllMenusByRestaurantId(Long id);
	public Image findImageById(Long id);
}