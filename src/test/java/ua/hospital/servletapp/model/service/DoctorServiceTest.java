package ua.hospital.servletapp.model.service;

import org.junit.Assert;
import org.junit.Test;

public class DoctorServiceTest {
	
	private DoctorService doctorService = new DoctorService();

	@Test
	public void testFindById() {
		Assert.assertEquals(true, doctorService.findById(1).isPresent());
		Assert.assertEquals(false, doctorService.findById(0).isPresent());
	}

	@Test
	public void testFindByUserLogin() {
		Assert.assertEquals(true, doctorService.findByUserLogin("Stevie").isPresent());
		Assert.assertEquals(false, doctorService.findByUserLogin("").isPresent());
	}

	@Test
	public void testFindAllPaginated() {
		Assert.assertEquals(false, doctorService.findAllPaginated(0, "last_name", "en").getList().isEmpty());
	}

}
