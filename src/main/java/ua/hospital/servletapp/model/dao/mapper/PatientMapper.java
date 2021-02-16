package ua.hospital.servletapp.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.model.entity.Person;

public class PatientMapper implements ObjectMapper<Patient> {

	@Override
	public Patient extractFromResultSet(ResultSet rs) throws SQLException {
		Person person = Person.builder()
				.withId(rs.getInt("pp.id"))
				.withFirstNameEn(rs.getString("pp.first_name_en"))
				.withFirstNameUk(rs.getString("pp.first_name_uk"))
				.withLastNameEn(rs.getString("pp.last_name_en"))
				.withLastNameUk(rs.getString("pp.last_name_uk"))
				.withBirthDate(Optional.ofNullable(rs.getTimestamp("pp.birth_date"))
						.map(o -> o.toLocalDateTime()).map(o -> o.toLocalDate()).orElse(null))
				.build();
		return Patient.builder()
				.withId(rs.getInt("p.id"))
				.withPerson(person)
				.withArriveDate(Optional.ofNullable(rs.getTimestamp("arrive_date"))
						.map(o -> o.toLocalDateTime()).orElse(null))
				.withDiagnosisEn(rs.getString("diagnosis_en"))
				.withDiagnosisUk(rs.getString("diagnosis_uk"))
				.withDischargeDateTime(Optional.ofNullable(rs.getTimestamp("discharge_date_time"))
						.map(o -> o.toLocalDateTime()).orElse(null))
				.build();
	}

	@Override
	public Patient makeUnique(Map<Integer, Patient> cache, Patient patient) {
		cache.putIfAbsent(patient.getId(), patient);
		return cache.get(patient.getId());
	}
	
}
