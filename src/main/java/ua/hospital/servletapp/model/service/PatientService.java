package ua.hospital.servletapp.model.service;

import java.util.Optional;
import java.util.stream.Collectors;

import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dao.PatientDao;
import ua.hospital.servletapp.model.dto.PatientDto;
import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.support.EntityDtoConverter;
import ua.hospital.servletapp.support.Page;

public class PatientService {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	
	public boolean createPatient(Patient patient) {
		PatientDao patientDao = daoFactory.createPatientDao();
		patientDao.create(patient);
		patientDao.close();
		return patient.getId() != 0;
	}
	
	public boolean assignDoctor(int patientId, int doctorId) {
		PatientDao patientDao = daoFactory.createPatientDao();
		boolean result = patientDao.assignDoctor(patientId, doctorId);
		patientDao.close();
		return result;
	}
	
	public boolean defineDiagnosis(int patientId, String diagnosisEn, String diagnosisUk) {
		PatientDao patientDao = daoFactory.createPatientDao();
		boolean result = patientDao.defineDiagnosis(patientId, diagnosisEn, diagnosisUk);
		patientDao.close();
		return result;
	}
	
	public boolean dischargePatient(int patientId) {
		PatientDao patientDao = daoFactory.createPatientDao();
		boolean result = patientDao.dischargePatient(patientId);
		patientDao.close();
		return result;
	}
	
	public boolean assignMeal(int patientId, int mealId) {
		PatientDao patientDao = daoFactory.createPatientDao();
		boolean result = patientDao.assignMeal(patientId, mealId);
		patientDao.close();
		return result;
	}
	
	public boolean removeMeal(int patientId, int mealId) {
		PatientDao patientDao = daoFactory.createPatientDao();
		boolean result = patientDao.removeMeal(patientId, mealId);
		patientDao.close();
		return result;
	}
	
	public Optional<PatientDto> findById (int patientId) {
		PatientDao patientDao = daoFactory.createPatientDao();
		Optional<PatientDto> result = patientDao.findById(patientId).map(EntityDtoConverter::PatientToDto);
		patientDao.close();
		return result;
	}
	
	public Page<PatientDto> findAllPaginated(int pageNumber, String sort, String locale){
		PatientDao patientDao = daoFactory.createPatientDao();
		Page<Patient> page = patientDao.findPatientsPaginated(pageNumber, addLocaleIfName(sort, locale), 0, null);
		patientDao.close();
		return new Page<PatientDto>(page.getList().stream()
				.map(EntityDtoConverter::PatientToDto).collect(Collectors.toList()),
				sort, 
				page.getPageNumber(), 
				page.getAllPages());
	}
	
	public Page<PatientDto> findByDoctorIdPaginated(int pageNumber, String sort, String locale, int doctorId){
		PatientDao patientDao = daoFactory.createPatientDao();
		Page<Patient> page = patientDao.findPatientsPaginated(pageNumber, addLocaleIfName(sort, locale), doctorId, null);
		patientDao.close();
		return new Page<PatientDto>(page.getList().stream()
				.map(EntityDtoConverter::PatientToDto).collect(Collectors.toList()),
				sort, 
				page.getPageNumber(), 
				page.getAllPages());
	}
	
	public Page<PatientDto> findByUserLoginPaginated(int pageNumber, String sort, String locale, String login){
		PatientDao patientDao = daoFactory.createPatientDao();
		Page<Patient> page = patientDao.findPatientsPaginated(pageNumber, addLocaleIfName(sort, locale), 0, login);
		patientDao.close();
		return new Page<PatientDto>(page.getList().stream()
				.map(EntityDtoConverter::PatientToDto).collect(Collectors.toList()),
				sort, 
				page.getPageNumber(), 
				page.getAllPages());
	}
	
	private String addLocaleIfName(String sort, String locale) {
		if ("last_name".equals(sort)) {
			return new StringBuilder(sort).append('_').append(locale).toString();			
		}
		return sort;
	}

}
