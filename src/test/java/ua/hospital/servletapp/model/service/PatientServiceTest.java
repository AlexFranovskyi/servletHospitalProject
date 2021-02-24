package ua.hospital.servletapp.model.service;

import org.junit.Assert;
import org.junit.Test;

import ua.hospital.servletapp.model.dto.PatientDto;

public class PatientServiceTest {
	
	private PatientService patientService = new PatientService();

	@Test
	public void testFindById() {
		PatientDto patient = patientService.findById(1).get();
		Assert.assertEquals(true, patient.toString() != null);
		Assert.assertEquals(false, patientService.findById(0).isPresent());
	}

	@Test
	public void testFindAllPaginated() {
		Assert.assertEquals(false, patientService.findAllPaginated(0, "last_name", "en").getList().isEmpty());
	}

	@Test
	public void testFindByDoctorIdPaginated() {
		Assert.assertEquals(true, patientService.findByDoctorIdPaginated(0, "last_name", "en", 8).getList().isEmpty());
		Assert.assertEquals(false, patientService.findByDoctorIdPaginated(0, "last_name", "en", 0).getList().isEmpty());
	}

	@Test
	public void testFindByUserLoginPaginated() {
		Assert.assertEquals(true, patientService.findByUserLoginPaginated(0, "last_name", "en", "Stevie").getList().isEmpty());
		Assert.assertEquals(false, patientService.findByUserLoginPaginated(0, "last_name", "en", "1").getList().size() > 0);
	}
	
	@Test
	public void testAssignMeal() {
		Assert.assertEquals(false, patientService.assignMeal(1, 0));
	}
	
	@Test
	public void testRemoveMeal() {
		Assert.assertEquals(false, patientService.removeMeal(1, 0));
	}

}
