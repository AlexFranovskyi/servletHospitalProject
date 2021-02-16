package ua.hospital.servletapp.model.dao;

import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.support.Page;

public interface PatientDao extends GenericDao<Patient>{
	boolean assignDoctor(int patientId, int doctorId);
	boolean defineDiagnosis(int patientId, String diagnosisEn, String diagnosisUk);
	boolean dischargePatient(int patientId);
	
	boolean assignMeal(int patientId, int mealId);
	boolean removeMeal(int patientId, int mealId);
	
	Page<Patient> findPatientsPaginated(int pageNumber, String sort, int doctorId, String userLogin);
}
