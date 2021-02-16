package ua.hospital.servletapp.model.dao;

import java.util.Optional;

import ua.hospital.servletapp.model.entity.User;

public interface UserDao extends GenericDao<User> {
	Optional<User> findUserByLogin(String login);

}
