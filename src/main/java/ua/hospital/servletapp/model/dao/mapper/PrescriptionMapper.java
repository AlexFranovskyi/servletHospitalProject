package ua.hospital.servletapp.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import ua.hospital.servletapp.model.entity.Prescription;

public class PrescriptionMapper implements ObjectMapper<Prescription> {

	@Override
	public Prescription extractFromResultSet(ResultSet rs) throws SQLException {
		return Prescription.builder()
				.withId(rs.getInt("pr.id"))
				.withPrescriptionType(Optional.ofNullable(rs.getString("prescription_type"))
						.map(Prescription.PrescriptionType::valueOf).orElse(null))
				.withDescriptionEn(rs.getString("description_en"))
				.withDescriptionUk(rs.getString("description_uk"))
				.withCompletionTime(Optional.ofNullable(rs.getTimestamp("completion_time"))
						.map(o -> o.toLocalDateTime()).orElse(null))
				.build();
	}

	@Override
	public Prescription makeUnique(Map<Integer, Prescription> cache, Prescription prescription) {
		cache.putIfAbsent(prescription.getId(), prescription);
		return cache.get(prescription.getId());
	}
	
	

}
