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

import ua.hospital.servletapp.model.dao.CategoryDao;
import ua.hospital.servletapp.model.dao.mapper.CategoryMapper;
import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.support.SqlQueries;

public class JdbcCategoryDao implements CategoryDao {
	private final static Logger logger = LogManager.getLogger(JdbcCategoryDao.class);
	
	private Connection connection;

	public JdbcCategoryDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Category create(Category category) {
		ResultSet rs = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS)){
			int k = 1;
			pstmt.setString(k++, category.getNameEn());
			pstmt.setString(k++, category.getNameUk());
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't insert new category into database");
				return category;
			}
			
			logger.info("new category is added to the database");
			rs = pstmt.getGeneratedKeys();
			rs.next();
			category.setId(rs.getInt(1));
					
		} catch (SQLException ex) {
			logger.error("Some error occured while inserting new category into the database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return category;
	}
		
	/**
	 * Method is inherited but not described. Not to use
	 */
	@Override
	public Optional<Category> findById(int id) {
		return Optional.empty();
	}

	@Override
	public List<Category> findAll() {
		ResultSet rs = null;
		List<Category> categories = new ArrayList<>();
		
		try (Statement stmt = connection.createStatement()) {
			rs = stmt.executeQuery(SqlQueries.SQL_SELECT_ALL_CATEGORIES);
			CategoryMapper categoryMapper = new CategoryMapper();
			
			while(rs.next()) {
				categories.add(categoryMapper.extractFromResultSet(rs));
			}
			
		} catch (SQLException ex) {
			logger.error("Some problem occurred while categories extracting from DB", ex);
		} finally {
			closeAutoclosable(rs);
		}
		
		return categories;
	}

	@Override
	public boolean update(Category category) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_UPDATE_CATEGORY)){
			int k = 1;
			pstmt.setString(k++, category.getNameEn());
			pstmt.setString(k++, category.getNameUk());
			pstmt.setInt(k++, category.getId());
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't update the category in database");
				return false;
			}
			
		} catch (SQLException ex) {
			logger.error("Some error occured while updating the category in the database", ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_DELETE_CATEGORY)){
			pstmt.setInt(1, id);
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't delete the category in the database");
				return false;
			}
			
		} catch (SQLException ex) {
			logger.error("Some error occured while deleting the category in the database", ex);
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
