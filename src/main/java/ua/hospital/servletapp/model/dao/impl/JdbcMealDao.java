package ua.hospital.servletapp.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dao.MealDao;
import ua.hospital.servletapp.model.dao.mapper.MealMapper;
import ua.hospital.servletapp.model.entity.Meal;
import ua.hospital.servletapp.support.SqlQueries;

public class JdbcMealDao implements MealDao {
	private final static Logger logger = LogManager.getLogger(JdbcMealDao.class);
	
	private Connection connection;
	
	public JdbcMealDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Meal create(Meal meal) {
		ResultSet rs = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_INSERT_MEAL, Statement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			pstmt.setString(k++, meal.getNameEn());
			pstmt.setString(k++, meal.getNameUk());
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't insert new meal into database");
				return meal;
			}
			
			logger.info("new meal is added to the database");
			rs = pstmt.getGeneratedKeys();
			rs.next();
			meal.setId(rs.getInt(1));
			
		} catch(SQLException ex) {
			logger.error("Some error occured while inserting new meal into the database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return meal;
	}

	@Override
	public Optional<Meal> findById(int id) {
		return null;
	}

	@Override
	public List<Meal> findAll() {
		ResultSet rs = null;
		List<Meal> meals = new ArrayList<>();
		
		try (Statement stmt = connection.createStatement()) {
			rs = stmt.executeQuery(SqlQueries.SQL_FIND_ALL_MEALS);
			MealMapper mealMapper = new MealMapper();
			
			while(rs.next()) {
				meals.add(mealMapper.extractFromResultSet(rs));
			}
		} catch (SQLException ex) {
			logger.error("Some problem occurred while meals extracting from the DB", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return meals;
	}

	@Override
	public boolean update(Meal meal) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_UPDATE_MEAL)){
			int k = 1;
			pstmt.setString(k++, meal.getNameEn());
			pstmt.setString(k++, meal.getNameUk());
			pstmt.setInt(k++, meal.getId());
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't update the meal in the database");
				return false;
			}
			
		} catch(SQLException ex) {
			logger.info("Some error occured while updating the meal in the database", ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_DELETE_MEAL)){
			pstmt.setInt(1, id);
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't delete the meal in the database");
				return false;
			}
			
		} catch (SQLException ex) {
			logger.error("Some error occured while deleting the meal in the database", ex);
			return false;
		}
		return true;
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
				throw new IllegalStateException("Cannot close Autoclosable object" + ac);
			}
		}
	}

}
