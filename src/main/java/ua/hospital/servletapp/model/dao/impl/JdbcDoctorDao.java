package ua.hospital.servletapp.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dao.DoctorDao;
import ua.hospital.servletapp.model.dao.mapper.CategoryMapper;
import ua.hospital.servletapp.model.dao.mapper.DoctorMapper;
import ua.hospital.servletapp.model.dao.mapper.UserMapper;
import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.model.entity.Doctor;
import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.entity.User;
import ua.hospital.servletapp.support.Constants;
import ua.hospital.servletapp.support.Page;
import ua.hospital.servletapp.support.SqlQueries;

public class JdbcDoctorDao implements DoctorDao {
	private final static Logger logger = LogManager.getLogger(JdbcDoctorDao.class);
	
	private Connection connection;

	public JdbcDoctorDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Doctor create(Doctor doctor) {
		User user = doctor.getUser();
		Person person = user.getPerson();
		
		ResultSet rsOne = null;
		ResultSet rsTwo = null;
		ResultSet rsThree = null;
		
		PreparedStatement pstmtOne = null;
		PreparedStatement pstmtTwo = null;
		PreparedStatement pstmtThree = null;

		try {
			connection.setAutoCommit(false);
			pstmtOne = connection.prepareStatement(SqlQueries.SQL_INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
			pstmtTwo = connection.prepareStatement(SqlQueries.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			pstmtThree = connection.prepareStatement(SqlQueries.SQL_INSERT_DOCTOR, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			pstmtOne.setString(k++, person.getFirstNameEn());
			pstmtOne.setString(k++, person.getFirstNameUk());
			pstmtOne.setString(k++, person.getLastNameEn());
			pstmtOne.setString(k++, person.getLastNameUk());
			pstmtOne.setString(k++, person.getBirthDate().toString());

			if (pstmtOne.executeUpdate() == 0) {
				logger.error("coudn't insert person entity into database");
				connection.setAutoCommit(true);
				return doctor;
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
				return doctor;
			}

			rsTwo = pstmtTwo.getGeneratedKeys();
			rsTwo.next();
			user.setId(rsTwo.getInt(1));
			
			k = 1;
			pstmtThree.setInt(k++, user.getId());
			
			if (pstmtThree.executeUpdate() == 0) {
				logger.info("coudn't insert doctor entity into database, doctor with such user id already exists");
				connection.rollback();
				connection.setAutoCommit(true);
				return doctor;
			}
			
			rsThree = pstmtThree.getGeneratedKeys();
			rsThree.next();
			doctor.setId(rsThree.getInt(1));
			
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("new doctor is added succesfully");
			
		} catch (SQLException e) {
			logger.error("Some mistake occured while doctor insertion into database", e);
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException ex) {
				logger.error("database access error occured", ex);
			}
		} finally {
			closeAutoclosable(rsThree);
			closeAutoclosable(rsTwo);
			closeAutoclosable(rsOne);
			
			closeAutoclosable(pstmtThree);
			closeAutoclosable(pstmtTwo);
			closeAutoclosable(pstmtOne);
		}
		return null;
	}

	@Override
	public Optional<Doctor> findById(int id) {
		ResultSet rs = null;
		Doctor doctor = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_DOCTOR_FIND_BY_ID)){
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			doctor = extractSingleDoctor(rs);
		} catch(SQLException ex) {
			logger.info("Couldn't extract user entity from database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return Optional.ofNullable(doctor);
	}

	@Override
	public Optional<Doctor> findByUserLogin(String login) {
		ResultSet rs = null;
		Doctor doctor = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_DOCTOR_FIND_BY_USER_LOGIN)){
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			doctor = extractSingleDoctor(rs);
		} catch(SQLException ex) {
			logger.info("Couldn't extract user entity from database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return Optional.ofNullable(doctor);
	}

	@Override
	public List<Doctor> findAll() {
		return null;
	}

	@Override
	public Page<Doctor> findAllPaginated(int pageNumber, String sort) {
		int offset = Constants.PAGE_SIZE * pageNumber;
		int limit = Constants.PAGE_SIZE;
		ResultSet rs = null;
		Page<Doctor> page = new Page<>(new ArrayList<>(), sort, 0, 0);
		
		String sqlQuery = SqlQueries.SQL_DOCTOR_FIND_ALL_PAGINATED;
		sqlQuery = String.format(sqlQuery, sort);
		
		try(PreparedStatement pstmt = connection.prepareStatement(sqlQuery)){
			int k = 1;
			
			pstmt.setInt(k++, limit);
			pstmt.setInt(k++, offset);
			rs = pstmt.executeQuery();
			logger.info("statement is executed");
			
			CategoryMapper categoryMapper = new CategoryMapper();
			DoctorMapper doctorMapper = new DoctorMapper();
			UserMapper userMapper = new UserMapper();
			
			Map<Integer, Doctor> doctors = new LinkedHashMap<>();
			Map<Integer, User> users = new HashMap<>();
			Map<Integer, Category> categories = new HashMap<>();
			
			User user = null;
			Category category = null;
			Doctor doctor = null;
			
			int totalRows = -1;
			
			while (rs.next()) {
				if (totalRows == -1) {
					totalRows = rs.getInt("total_rows");
				}

				user = userMapper.extractFromResultSet(rs);
				category = categoryMapper.extractFromResultSet(rs);
				doctor = doctorMapper.extractFromResultSet(rs);

				user = userMapper.makeUnique(users, user);
				category = categoryMapper.makeUnique(categories, category);
				doctor = doctorMapper.makeUnique(doctors, doctor);

				doctor.setCategory(category);
				doctor.setUser(user);
			}
			List<Doctor> list = new ArrayList<Doctor>(doctors.values());
			int newPageNumber = offset / Constants.PAGE_SIZE;
			int allPages = totalRows / Constants.PAGE_SIZE + 1;
			page = new Page<Doctor>(list, sort, newPageNumber, allPages);
			
		} catch (SQLException ex) {
			logger.error("Some error ocured while extracting list of doctors from the database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return page;
	}

	@Override
	public boolean update(Doctor entity) {
		return false;
	}
	
	@Override
	public boolean assignCategory(int doctorId, int categoryId) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_ASSIGN_CATEGORY)){
			int k = 1;
			pstmt.setInt(k++, categoryId);
			pstmt.setInt(k++, doctorId);
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't assign the category to doctor in the database");
				return false;
			}
			
		} catch (SQLException ex) {
			logger.error("Some error occured while assigning a category into the database", ex);
			return false;
		}
		return true;
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
        	logger.error("database access error occured");
            throw new RuntimeException(e);
        }
	}
	
	private void closeAutoclosable(AutoCloseable ac) {
		if (ac != null) {
			try {
				ac.close();
			} catch (Exception ex) {
				throw new IllegalStateException("Cannot close AutoCloseable object" + ac);
			}
		}
	}
	
	/**
	 * 
	 * Additional method for extracting a single entity object from database query's ResultSet
	 * 
	 * @param rs - {@link ResultSet} object containing results of querying the database;
	 * @return {@link Doctor} object 
	 * @throws SQLException
	 */
	
	private Doctor extractSingleDoctor(ResultSet rs) throws SQLException {
		Doctor doctor = null;
		DoctorMapper doctorMapper = new DoctorMapper();
		CategoryMapper categoryMapper = new CategoryMapper();
		UserMapper userMapper = new UserMapper();
		if (rs.next()) {
			doctor = doctorMapper.extractFromResultSet(rs);
			doctor.setUser(userMapper.extractFromResultSet(rs));
			doctor.setCategory(categoryMapper.extractFromResultSet(rs));
			logger.info("Doctor entity is found successfully");
		}
		return doctor;
	}

}
