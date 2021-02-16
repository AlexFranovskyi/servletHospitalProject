package ua.hospital.servletapp.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import ua.hospital.servletapp.model.entity.Doctor;

public class DoctorMapper implements ObjectMapper<Doctor>{

	@Override
	public Doctor extractFromResultSet(ResultSet rs) throws SQLException {
		return Doctor.builder()
				.withId(rs.getInt("d.id"))
				.withPatientAmount(rs.getInt("patient_amount"))
				.build();
	}

	@Override
	public Doctor makeUnique(Map<Integer, Doctor> cache, Doctor doctor) {
		cache.putIfAbsent(doctor.getId(), doctor);
		return cache.get(doctor.getId());
	}
	
}
