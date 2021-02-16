package ua.hospital.servletapp.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import ua.hospital.servletapp.model.entity.Category;

public class CategoryMapper implements ObjectMapper<Category>{

	@Override
	public Category extractFromResultSet(ResultSet rs) throws SQLException {
		return Category.builder()
				.withId(rs.getInt("c.id"))
				.withNameEn(rs.getString("c.name_en"))
				.withNameUk(rs.getString("c.name_uk"))
				.build();
	}

	@Override
	public Category makeUnique(Map<Integer, Category> cache, Category category) {
		cache.putIfAbsent(category.getId(), category);
		return cache.get(category.getId());
	}

}
