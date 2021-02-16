package ua.hospital.servletapp.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dao.UserDao;
import ua.hospital.servletapp.model.dao.mapper.UserMapper;
import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.entity.User;
import ua.hospital.servletapp.support.SqlQueries;

public class JdbcUserDao implements UserDao {
	private final static Logger logger = LogManager.getLogger(JdbcUserDao.class);

	private Connection connection;

	public JdbcUserDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User create(User user) {
		Person person = user.getPerson();
		ResultSet rsOne = null;
		ResultSet rsTwo = null;
		PreparedStatement pstmtOne = null;
		PreparedStatement pstmtTwo = null;

		try {
			connection.setAutoCommit(false);
			pstmtOne = connection.prepareStatement(SqlQueries.SQL_INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
			pstmtTwo = connection.prepareStatement(SqlQueries.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			pstmtOne.setString(k++, person.getFirstNameEn());
			pstmtOne.setString(k++, person.getFirstNameUk());
			pstmtOne.setString(k++, person.getLastNameEn());
			pstmtOne.setString(k++, person.getLastNameUk());
			pstmtOne.setString(k++, person.getBirthDate().toString());

			if (pstmtOne.executeUpdate() == 0) {
				logger.error("coudn't insert person entity into database");
				connection.setAutoCommit(true);
				return user;
			}

			rsOne = pstmtOne.getGeneratedKeys();
			rsOne.next();
			person.setId(rsOne.getInt(1));
			k = 1;

			pstmtTwo.setInt(k++, person.getId());
			pstmtTwo.setString(k++, user.getRole().toString());
			pstmtTwo.setString(k++, user.getPassword());
			pstmtTwo.setString(k++, user.getLogin());

			if (pstmtTwo.executeUpdate() == 0) {
				logger.info("coudn't insert user entity into database, user with such login already exists");
				connection.rollback();
				connection.setAutoCommit(true);
				return user;
			}

			rsTwo = pstmtTwo.getGeneratedKeys();
			rsTwo.next();
			user.setId(rsTwo.getInt(1));
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("new user is added succesfully");
			
		} catch (SQLException e) {
			logger.error("database access error occured", e);
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException ex) {
				logger.error("database access error occured", ex);
			}
		} finally {
			closeAutoclosable(rsTwo);
			closeAutoclosable(rsOne);
			closeAutoclosable(pstmtTwo);
			closeAutoclosable(pstmtOne);
		}
		
		return user;
	}
	
	

	@Override
	public Optional<User> findUserByLogin(String login) {
		ResultSet rs = null;
		User user = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_USER_FIND_BY_LOGIN)){
			UserMapper mapper = new UserMapper();
			pstmt.setString(1, login);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = mapper.extractFromResultSet(rs);
				logger.info("User entity is found successfully according to login");
			}
		} catch(SQLException ex) {
			logger.info("Couldn't extract user entity from database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> findById(int id) {
		ResultSet rs = null;
		User user = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_USER_FIND_BY_ID)){
			UserMapper mapper = new UserMapper();
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = mapper.extractFromResultSet(rs);
				logger.info("User entity is found successfully according to id");
			}
		} catch(SQLException ex) {
			logger.info("Couldn't extract user entity from database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return Optional.ofNullable(user);
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public boolean update(User user) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error("database access error occured, while connection closing", e);
			throw new RuntimeException(e);
		}

	}
	
	private void closeAutoclosable(AutoCloseable ac) {
		if (ac != null) {
			try {
				ac.close();
			} catch (Exception ex) {
				throw new IllegalStateException("Cannot close Autoclosable object" + ac);
			}
		}
	}

}
