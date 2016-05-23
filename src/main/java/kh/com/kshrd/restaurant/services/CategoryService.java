package kh.com.kshrd.restaurant.services;

import java.util.List;

import kh.com.kshrd.restaurant.filters.CategoryFilter;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.utilities.Pagination;

public interface CategoryService {

	public boolean addNewCategory(Category category);
	public boolean updateCategory(Category category);
	public List<Category> getAllCategories(CategoryFilter filter, Pagination pagination);
	public List<Category> getAllCategoriesByRestaurantId(Long id);
	public Category getCategoryById(Long id);
}
