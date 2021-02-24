package ua.hospital.servletapp.model.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ua.hospital.servletapp.model.dto.MealDto;
import ua.hospital.servletapp.model.entity.Meal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MealServiceTest {
	
	private MealService mealService = new MealService();
	
	private static final Meal MEAL_A = new Meal("Test123", "Тест123");
	private MealDto meal = new MealDto();

	@Test
	public void testACreateUpdateDeleteMeal() {
		Assert.assertEquals(true, mealService.createMeal(MEAL_A));
	}

	@Test
	public void testBUpdateMeal() {
		List<MealDto> list = mealService.findAll();
		meal = list.stream().filter(o -> o.getNameEn().equals("Test123")).findFirst().get();
		Assert.assertEquals(true, mealService.updateMeal(new Meal(meal.getId(), "TestNew123", "ТестНью123")));
	}

	@Test
	public void testCDeleteMeal() {
		List<MealDto> list = mealService.findAll();
		meal = list.stream().filter(o -> o.getNameEn().equals("TestNew123")).findFirst().get();
		Assert.assertEquals(true, mealService.deleteMeal(meal.getId()));
	}

	@Test
	public void testDFindAll() {
		List<MealDto> list = mealService.findAll();
		Assert.assertNotNull(list);
	}

}
