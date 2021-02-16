package ua.hospital.servletapp.model.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dao.UserDao;
import ua.hospital.servletapp.model.dto.UserDto;
import ua.hospital.servletapp.model.entity.User;
import ua.hospital.servletapp.support.EntityDtoConverter;
import ua.hospital.servletapp.support.HashEncryptor;

public class UserService {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	public boolean createUser(User user) {
		UserDao userDao = daoFactory.createUserDao();
		String password = user.getPassword();
		
		try {
			password = HashEncryptor.encryptString(password);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Coudn't encrypt password", e);
			e.printStackTrace();
		}
		user.setPassword(password);
		userDao.create(user);
		userDao.close();
		return user.getId() != 0;
	}
	
	public Optional<UserDto> authorizeByLoginAndPassword(String login, String password){
		UserDao userDao = daoFactory.createUserDao();
		Optional<UserDto> optional = Optional.empty();
		
		try {
			String encriptedPassword = HashEncryptor.encryptString(password);
			
			optional = userDao.findUserByLogin(login)
					.filter(u -> u.getPassword().equals(encriptedPassword))
					.map(EntityDtoConverter::UserToDto);
			
		} catch (NoSuchAlgorithmException e) {
			logger.error("Some problem occured with password encription", e);
			e.printStackTrace();
		}
		
		userDao.close();		
		return optional;
	}
	
	public Optional<UserDto> findById(int id){
		UserDao userDao = daoFactory.createUserDao();
		Optional<UserDto> optional = userDao.findById(id).map(EntityDtoConverter::UserToDto);
		userDao.close();
		return optional;
	}
	
	public Optional<UserDto> findByLogin(String login){
		UserDao userDao = daoFactory.createUserDao();
		Optional<UserDto> optional = userDao.findUserByLogin(login).map(EntityDtoConverter::UserToDto);
		userDao.close();
		return optional;
	}
	

}
