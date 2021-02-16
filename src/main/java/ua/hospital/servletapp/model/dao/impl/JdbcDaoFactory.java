package ua.hospital.servletapp.model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dao.CategoryDao;
import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dao.DoctorDao;
import ua.hospital.servletapp.model.dao.MealDao;
import ua.hospital.servletapp.model.dao.PatientDao;
import ua.hospital.servletapp.model.dao.PrescriptionDao;
import ua.hospital.servletapp.model.dao.UserDao;

public class JdbcDaoFactory extends DaoFactory {
	
	private static final Logger logger = LogManager.getLogger(JdbcDaoFactory.class);
	
	private DataSource dataSource = ConnectionPoolHolder.getDataSource();

	@Override
	public UserDao createUserDao() {
		return new JdbcUserDao(getConnection());
	}

	@Override
	public PrescriptionDao createPrescriptionDao() {
		return new JdbcPrescriptionDao(getConnection());
	}

	@Override
	public PatientDao createPatientDao() {
		return new JdbcPatientDao(getConnection());
	}

	@Override
	public MealDao createMealDao() {
		return new JdbcMealDao(getConnection());
	}

	@Override
	public DoctorDao createDoctorDao() {
		return new JdbcDoctorDao(getConnection());
	}

	@Override
	public CategoryDao createCategoryDao() {
		return new JdbcCategoryDao(getConnection());
	}
	
	private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
        	logger.error("database access error occured");
            throw new RuntimeException(e);
        }
    }

}
