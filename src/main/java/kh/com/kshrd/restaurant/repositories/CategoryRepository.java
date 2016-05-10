package kh.com.kshrd.restaurant.repositories;

import java.util.List;

import kh.com.kshrd.restaurant.filters.CategoryFilter;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.utilities.Pagination;

public interface CategoryRepository {

	public List<Category> getAllCategories(CategoryFilter filter, Pagination pagination);
	public List<Category> getAllCategoriesByRestaurantId(Long id);
	public Category getCategoryById(Long id);
	public Long save(Category category);
	public boolean update(Category category);
}
