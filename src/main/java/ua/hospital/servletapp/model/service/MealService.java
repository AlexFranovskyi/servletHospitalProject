package ua.hospital.servletapp.model.service;

import java.util.ArrayList;
import java.util.List;

import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dao.MealDao;
import ua.hospital.servletapp.model.dto.MealDto;
import ua.hospital.servletapp.model.entity.Meal;
import ua.hospital.servletapp.support.EntityDtoConverter;

public class MealService {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	
	public boolean createMeal(Meal meal) {
		MealDao mealDao = daoFactory.createMealDao();
		mealDao.create(meal);
		mealDao.close();
		return meal.getId() != 0;
	}
	
	public boolean updateMeal(Meal meal) {
		MealDao mealDao = daoFactory.createMealDao();
		boolean result = mealDao.update(meal);
		mealDao.close();
		return result;
	}
	
	public boolean deleteMeal(int mealId) {
		MealDao mealDao = daoFactory.createMealDao();
		boolean result = mealDao.delete(mealId);
		mealDao.close();
		return result;
	}
	
	public List<MealDto> findAll(){
		MealDao mealDao = daoFactory.createMealDao();
		List<MealDto> meals = new ArrayList<>();
		
		mealDao.findAll().stream().map(EntityDtoConverter::MealToDto).forEach(meals::add);
		mealDao.close();
		return meals;
	}

}
