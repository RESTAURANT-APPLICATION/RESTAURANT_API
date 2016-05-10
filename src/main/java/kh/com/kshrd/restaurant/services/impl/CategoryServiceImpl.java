package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.restaurant.filters.CategoryFilter;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.repositories.CategoryRepository;
import kh.com.kshrd.restaurant.services.CategoryService;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategoriesByRestaurantId(Long id) {
		try{
			return categoryRepository.getAllCategoriesByRestaurantId(id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Category> getAllCategories(CategoryFilter filter, Pagination pagination) {
		try{
			return categoryRepository.getAllCategories(filter, pagination);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean addNewCategory(Category category) {
		try{
			if(categoryRepository.save(category) > 0){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updateCategory(Category category) {
		try{
			if(categoryRepository.update(category)){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	
}
