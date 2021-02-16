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

import ua.hospital.servletapp.model.dao.PrescriptionDao;
import ua.hospital.servletapp.model.entity.Prescription;
import ua.hospital.servletapp.support.SqlQueries;

public class JdbcPrescriptionDao implements PrescriptionDao {
	private final static Logger logger = LogManager.getLogger(JdbcPrescriptionDao.class);
	
	private Connection connection;
	
	public JdbcPrescriptionDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Prescription create(Prescription prescription) {
		ResultSet rs = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_INSERT_PRESCRIPTION, Statement.RETURN_GENERATED_KEYS)){
			int k = 1;
			pstmt.setString(k++, prescription.getDescriptionEn());
			pstmt.setString(k++, prescription.getDescriptionUk());
			pstmt.setString(k++, prescription.getType().toString());
			pstmt.setInt(k++, prescription.getPatient().getId());
			
			if (pstmt.executeUpdate() == 0) {
				logger.info("couldn't insert new prescription into the database");
				return prescription;
			}
			
			logger.info("new prescription is added to the database");
			rs = pstmt.getGeneratedKeys();
			rs.next();
			prescription.setId(rs.getInt(1));
			
		} catch (SQLException ex) {
			logger.error("Some error occured while inserting new prescription into database", ex);
		} finally {
			closeAutoclosable(rs);
		}
		return prescription;
	}

	@Override
	public Optional<Prescription> findById(int id) {
		return null;
	}

	@Override
	public List<Prescription> findAll() {
		return null;
	}

	@Override
	public boolean update(Prescription entity) {
		return false;
	}

	@Override
	public boolean completePrescription(int prescriptionId) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_COMPLETE_PRESCRIPTION)){
			pstmt.setInt(1, prescriptionId);
			if (pstmt.executeUpdate() == 0) {
				logger.info("Couldn't complete the prescription in the database");
				return false;
			}
		} catch (SQLException ex) {
			logger.error("Some error occured while updating the prescription in the database", ex);
			return false;
		}
		logger.info("The prescription is complete in the database");
		return true;
	}

	@Override
	public boolean delete(int id) {
		try(PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_DELETE_PRESCRIPTION)){
			pstmt.setInt(1, id);
			if (pstmt.executeUpdate() == 0) {
				logger.info("Couldn't delete the prescription in the database");
				return false;
			}
		} catch (SQLException ex) {
			logger.error("Some error occured while deleting the prescription in the database", ex);
			return false;
		}
		logger.info("The prescription is deleted in the database");
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
