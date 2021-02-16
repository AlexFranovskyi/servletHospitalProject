package ua.hospital.servletapp.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.entity.User;

public class UserMapper implements ObjectMapper<User>{

	@Override
	public User extractFromResultSet(ResultSet rs) throws SQLException {
		Person person = Person.builder()
				.withId(rs.getInt("up.id"))
				.withFirstNameEn(rs.getString("up.first_name_en"))
				.withFirstNameUk(rs.getString("up.first_name_uk"))
				.withLastNameEn(rs.getString("up.last_name_en"))
				.withLastNameUk(rs.getString("up.last_name_uk"))
				.withBirthDate(Optional.ofNullable(rs.getTimestamp("up.birth_date"))
						.map(o -> o.toLocalDateTime()).map(o -> o.toLocalDate()).orElse(null))
				.build();
		return User.builder()
				.withId(rs.getInt("u.id"))
				.withLogin(rs.getString("login"))
				.withPassword(rs.getString("password"))
				.withRole(Optional.ofNullable(rs.getString("role")).map(User.Role::valueOf).orElse(null))
				.withPerson(person)
				.build();
	}

	@Override
	public User makeUnique(Map<Integer, User> cache, User user) {
		cache.putIfAbsent(user.getId(), user);
		return cache.get(user.getId());
	}

}
