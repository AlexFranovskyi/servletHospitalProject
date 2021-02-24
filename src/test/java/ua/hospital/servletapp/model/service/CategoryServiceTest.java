package ua.hospital.servletapp.model.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ua.hospital.servletapp.model.dto.CategoryDto;
import ua.hospital.servletapp.model.entity.Category;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryServiceTest {
	
	private CategoryService categoryService = new CategoryService();
	
	private static final Category CATEGORY_A = new Category("Test123", "Тест123");
	private CategoryDto category = new CategoryDto();
	
	@Test
	public void testACreateUpdateDeleteCategory() {
		Assert.assertEquals(true, categoryService.createCategory(CATEGORY_A));		
	}
	
	@Test
	public void testBUpdateCategory() {
		List<CategoryDto> list = categoryService.findAll();
		category = list.stream().filter(o -> o.getNameEn().equals("Test123")).findFirst().get();
		Assert.assertEquals(true, categoryService.updateCategory(new Category(category.getId(), "TestNew123", "ТестНью123")));
	}
	
	@Test
	public void testCDeleteCategory() {
		List<CategoryDto> list = categoryService.findAll();
		category = list.stream().filter(o -> o.getNameEn().equals("TestNew123")).findFirst().get();
		Assert.assertEquals(true, categoryService.deleteCategory(category.getId()));
	}

	@Test
	public void testDFindAll() {
		List<CategoryDto> list = categoryService.findAll();
		Assert.assertNotNull(list);
	}

}
