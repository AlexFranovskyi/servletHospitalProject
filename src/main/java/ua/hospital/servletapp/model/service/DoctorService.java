package ua.hospital.servletapp.model.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dao.DoctorDao;
import ua.hospital.servletapp.model.dto.DoctorDto;
import ua.hospital.servletapp.model.entity.Doctor;
import ua.hospital.servletapp.support.EntityDtoConverter;
import ua.hospital.servletapp.support.HashEncryptor;
import ua.hospital.servletapp.support.Page;

public class DoctorService {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	
	private static final Logger logger = LogManager.getLogger(DoctorService.class);
	
	public boolean createDoctor(Doctor doctor) {
		DoctorDao doctorDao = daoFactory.createDoctorDao();
		String password = doctor.getUser().getPassword();
		
		try {
			password = HashEncryptor.encryptString(password);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Coudn't encrypt password", e);
			e.printStackTrace();
		}
		doctor.getUser().setPassword(password);
		doctorDao.create(doctor);
		return doctor.getId() != 0;
	}
	
	public boolean assignCategory(int doctorId, int categoryId) {
		DoctorDao doctorDao = daoFactory.createDoctorDao();
		boolean result = doctorDao.assignCategory(doctorId, categoryId);
		doctorDao.close();		
		return result;
	}
	
	public Optional<DoctorDto> findById(int id){
		DoctorDao doctorDao = daoFactory.createDoctorDao();
		Optional<DoctorDto> optional = doctorDao.findById(id)
				.map(EntityDtoConverter::DoctorToDto);
		doctorDao.close();
		return optional;
	}
	
	public Optional<DoctorDto> findByUserLogin(String login){
		DoctorDao doctorDao = daoFactory.createDoctorDao();
		Optional<DoctorDto> optional = doctorDao.findByUserLogin(login)
				.map(EntityDtoConverter::DoctorToDto);
		doctorDao.close();
		return optional;
	}
	
	public Page<DoctorDto> findAllPaginated(int pageNumber, String sort, String locale){
		DoctorDao doctorDao = daoFactory.createDoctorDao();
		Page<Doctor> page = doctorDao.findAllPaginated(pageNumber, addLocaleIfName(sort, locale));
		doctorDao.close();
		return new Page<DoctorDto>(page.getList().stream()
				.map(EntityDtoConverter::DoctorToDto).collect(Collectors.toList()),
				sort,
				page.getPageNumber(),
				page.getAllPages());
	}
	
	private String addLocaleIfName(String sort, String locale) {
		if ("last_name".equals(sort) || "c.name".equals(sort)) {
			return new StringBuilder(sort).append('_').append(locale).toString();			
		}
		return sort;
	}

}
