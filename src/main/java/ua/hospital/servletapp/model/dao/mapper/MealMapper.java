package ua.hospital.servletapp.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import ua.hospital.servletapp.model.entity.Meal;

public class MealMapper implements ObjectMapper<Meal> {

	@Override
	public Meal extractFromResultSet(ResultSet rs) throws SQLException {
		return Meal.builder()
				.withId(rs.getInt("m.id"))
				.withNameEn(rs.getString("m.name_en"))
				.withNameUk(rs.getString("m.name_uk"))
				.build();
	}

	public Meal makeUnique(Map<Integer, Meal> cache, Meal meal) {
		cache.putIfAbsent(meal.getId(), meal);
		return cache.get(meal.getId());
	}

}
