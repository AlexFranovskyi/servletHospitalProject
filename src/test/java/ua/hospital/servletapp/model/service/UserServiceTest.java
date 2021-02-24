package ua.hospital.servletapp.model.service;

import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest {
	
	private UserService userService = new UserService();

	@Test
	public void testAuthorizeByLoginAndPassword() {
		Assert.assertEquals(true, userService.authorizeByLoginAndPassword("John", "John1").isPresent());
		Assert.assertEquals(false, userService.authorizeByLoginAndPassword("John", "Jo").isPresent());
	}

	@Test
	public void testFindById() {
		Assert.assertEquals(true, userService.findById(1).isPresent());
		Assert.assertEquals(false, userService.findById(-1).isPresent());
	}

	@Test
	public void testFindByLogin() {
		Assert.assertEquals(true, userService.findByLogin("John").isPresent());
		Assert.assertEquals(false, userService.findByLogin("").isPresent());
	}

}
