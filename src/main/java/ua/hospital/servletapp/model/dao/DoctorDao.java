package ua.hospital.servletapp.model.dao;

import java.util.Optional;

import ua.hospital.servletapp.model.entity.Doctor;
import ua.hospital.servletapp.support.Page;

public interface DoctorDao extends GenericDao<Doctor> {
	boolean assignCategory(int doctorId, int categoryId);
	Optional<Doctor> findByUserLogin(String login);
	Page<Doctor> findAllPaginated(int pageNumber, String sort);

}
