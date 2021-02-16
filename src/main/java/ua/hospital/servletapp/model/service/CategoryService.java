package ua.hospital.servletapp.model.service;

import java.util.ArrayList;
import java.util.List;

import ua.hospital.servletapp.model.dao.CategoryDao;
import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dto.CategoryDto;
import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.support.EntityDtoConverter;

public class CategoryService {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	
	public boolean createCategory(Category category) {
		CategoryDao categoryDao = daoFactory.createCategoryDao();
		categoryDao.create(category);
		categoryDao.close();
		return category.getId() != 0;
	}
	
	public boolean updateCategory(Category category) {
		CategoryDao categoryDao = daoFactory.createCategoryDao();
		boolean result = categoryDao.update(category);
		categoryDao.close();
		return result;
	}
	
	public boolean deleteCategory(int id) {
		CategoryDao categoryDao = daoFactory.createCategoryDao();
		boolean result = categoryDao.delete(id);
		categoryDao.close();
		return result;
	}
	
	public List<CategoryDto> findAll(){
		CategoryDao categoryDao = daoFactory.createCategoryDao();
		List<CategoryDto> categories = new ArrayList<>();
		
		categoryDao.findAll().stream()
		.map(EntityDtoConverter::CategoryToDto).forEach(categories::add);
		categoryDao.close();
		return categories;
	}

}
