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

import ua.hospital.servletapp.model.dao.PatientDao;
import ua.hospital.servletapp.model.dao.mapper.CategoryMapper;
import ua.hospital.servletapp.model.dao.mapper.DoctorMapper;
import ua.hospital.servletapp.model.dao.mapper.MealMapper;
import ua.hospital.servletapp.model.dao.mapper.PatientMapper;
import ua.hospital.servletapp.model.dao.mapper.PrescriptionMapper;
import ua.hospital.servletapp.model.dao.mapper.UserMapper;
import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.model.entity.Doctor;
import ua.hospital.servletapp.model.entity.Meal;
import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.entity.Prescription;
import ua.hospital.servletapp.model.entity.User;
import ua.hospital.servletapp.support.Constants;
import ua.hospital.servletapp.support.Page;
import ua.hospital.servletapp.support.SqlQueries;

public class JdbcPatientDao implements PatientDao {
	private final static Logger logger = LogManager.getLogger(JdbcPatientDao.class);

	private Connection connection;

	public JdbcPatientDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Patient create(Patient patient) {
		Person person = patient.getPerson();

		ResultSet rsOne = null;
		ResultSet rsTwo = null;
		PreparedStatement pstmtOne = null;
		PreparedStatement pstmtTwo = null;

		try {
			connection.setAutoCommit(false);
			pstmtOne = connection.prepareStatement(SqlQueries.SQL_INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
			pstmtTwo = connection.prepareStatement(SqlQueries.SQL_INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			pstmtOne.setString(k++, person.getFirstNameEn());
			pstmtOne.setString(k++, person.getFirstNameUk());
			pstmtOne.setString(k++, person.getLastNameEn());
			pstmtOne.setString(k++, person.getLastNameUk());
			pstmtOne.setString(k++, person.getBirthDate().toString());

			if (pstmtOne.executeUpdate() == 0) {
				logger.error("couldn't insert person entity into database");
				connection.setAutoCommit(true);
				return patient;
			}

			rsOne = pstmtOne.getGeneratedKeys();
			rsOne.next();
			person.setId(rsOne.getInt(1));

			k = 1;
			pstmtTwo.setInt(k++, person.getId());
			if (pstmtTwo.executeUpdate() == 0) {
				logger.info("couldn't insert patient entity into database");
				connection.rollback();
				connection.setAutoCommit(true);
				return patient;
			}

			rsTwo = pstmtTwo.getGeneratedKeys();
			rsTwo.next();
			patient.setId(rsTwo.getInt(1));
			connection.commit();
			connection.setAutoCommit(true);
			logger.info("new patient is added succesfully into the database");

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

		return patient;
	}

	@Override
	public Optional<Patient> findById(int id) {
		Patient patient = null;
		ResultSet rs = null;

		try (PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_PATIENT_FIND_BY_ID)) {
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			logger.info("statement is executed");

			CategoryMapper categoryMapper = new CategoryMapper();
			DoctorMapper doctorMapper = new DoctorMapper();
			MealMapper mealMapper = new MealMapper();
			PatientMapper patientMapper = new PatientMapper();
			PrescriptionMapper prescriptionMapper = new PrescriptionMapper();
			UserMapper userMapper = new UserMapper();

			Map<Integer, Meal> meals = new HashMap<>();
			Map<Integer, Prescription> prescriptions = new HashMap<>();

			User user = null;
			Category category = null;
			Doctor doctor = null;
			Meal meal = null;
			Prescription prescription = null;

			while (rs.next()) {
				if (patient == null) {
					user = userMapper.extractFromResultSet(rs);
					category = categoryMapper.extractFromResultSet(rs);
					doctor = doctorMapper.extractFromResultSet(rs);
					doctor.setCategory(category);
					doctor.setUser(user);
					patient = patientMapper.extractFromResultSet(rs);
					patient.setDoctor(doctor);
				}

				meal = mealMapper.extractFromResultSet(rs);
				meal = mealMapper.makeUnique(meals, meal);
				if (meal.getId() > 0) {
					patient.getMeals().add(meal);				
				}

				prescription = prescriptionMapper.extractFromResultSet(rs);
				prescription = prescriptionMapper.makeUnique(prescriptions, prescription);
				prescription.setPatient(patient);

				if (!patient.getPrescriptions().contains(prescription) && prescription.getId() > 0) {
					patient.getPrescriptions().add(prescription);
				}
			}
			logger.info("Patient entity is formed");

		} catch (SQLException ex) {
			logger.error("Some error ocured while extracting patient entity", ex);
		} finally {
			closeAutoclosable(rs);
		}

		return Optional.ofNullable(patient);
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> list = new ArrayList<>();
		return list;
	}
	
	/**
	 * This method should be called with next values of parameters "doctorId" and "userLogin":
	 * 
	 * 1) doctorId == 0 and userLogin != null;
	 * 2) doctorId != 0 and userLogin == null;
	 * 3) doctorId == 0 and userLogin == null
	 * 
	 * Using "doctorId" != 0 and "userLogin" != null will cause SQLException to be thrown.
	 */

	@Override
	public Page<Patient> findPatientsPaginated(int pageNumber, String sort, int doctorId, String userLogin) {
		int offset = Constants.PAGE_SIZE * pageNumber;
		int limit = Constants.PAGE_SIZE;
		ResultSet rs = null;
		Page<Patient> page = new Page<>(new ArrayList<>(), sort, 0, 0);

		String sqlQuery = SqlQueries.SQL_PATIENT_FIND_ALL_PAGINATED;
		
		if (doctorId != 0) {
			sqlQuery = SqlQueries.SQL_PATIENT_FIND_PAGINATED_BY_DOCTOR_ID;
		}
		if (userLogin != null) {
			sqlQuery = SqlQueries.SQL_PATIENT_FIND_PAGINATED_BY_DOCTOR_USER_LOGIN;
		}

		sqlQuery = String.format(sqlQuery, sort);

		try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
			int k = 1;

			if (doctorId != 0) {
				pstmt.setInt(k++, doctorId);
				pstmt.setInt(k++, doctorId);
			}
			if (userLogin != null) {
				pstmt.setString(k++, userLogin);
				pstmt.setString(k++, userLogin);
			}

			pstmt.setInt(k++, limit);
			pstmt.setInt(k++, offset);
			rs = pstmt.executeQuery();
			logger.info("statement is executed");
			page = extractPatientPage(rs, sort, offset);
			logger.info("list of patients is formed");
		} catch (SQLException ex) {
			logger.error("Some error ocured while extracting list of patients", ex);
		} finally {
			closeAutoclosable(rs);
		}

		return page;
	}

	@Override
	public boolean update(Patient entity) {
		return false;
	}

	@Override
	public boolean assignDoctor(int patientId, int doctorId) {
		try (PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_ASSIGN_DOCTOR)) {
			int k = 1;
			pstmt.setInt(k++, doctorId);
			pstmt.setInt(k++, patientId);

			if (pstmt.executeUpdate() == 0) {
				logger.info("Couldn't assign the doctor to patient in the database");
				return false;
			}

		} catch (SQLException ex) {
			logger.error("Some error occured while assigning a doctor to patient in the database", ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean defineDiagnosis(int patientId, String diagnosisEn, String diagnosisUk) {
		try (PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_DEFINE_DIAGNOSIS)) {
			int k = 1;
			pstmt.setString(k++, diagnosisEn);
			pstmt.setString(k++, diagnosisUk);
			pstmt.setInt(k++, patientId);

			if (pstmt.executeUpdate() == 0) {
				logger.info("Couldn't define the diagnosis to patient in the database");
				return false;
			}

		} catch (SQLException ex) {
			logger.error("Some error occured while defining the diagnosis to patient in the database", ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean dischargePatient(int patientId) {
		try (PreparedStatement pstmtOne = connection.prepareStatement(SqlQueries.SQL_DELETE_NOT_NEEDED_PRESCRIPTIONS_BY_PATIENT);
				PreparedStatement pstmtTwo = connection.prepareStatement(SqlQueries.SQL_DISCHARGE_PATIENT)) {
			pstmtOne.setInt(1, patientId);
			pstmtTwo.setInt(1, patientId);
			if (pstmtOne.executeUpdate() != 0) {
				logger.info("Some prescriptions were deleted as not needed anymore");
			}
			if (pstmtTwo.executeUpdate() == 0) {
				logger.info("Couldn't discharge the patient in the database");
				return false;
			}

		} catch (SQLException ex) {
			logger.error("Some error occured while discharging the patient in the database", ex);
			return false;
		}
		logger.info("The patient is discharged successfully in the database");
		return true;
	}

	@Override
	public boolean assignMeal(int patientId, int mealId) {
		try (PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_ASSIGN_PATIENT_MEAL)) {
			int k = 1;
			pstmt.setInt(k++, patientId);
			pstmt.setInt(k++, mealId);
			if (pstmt.executeUpdate() == 0) {
				logger.info("Couldn't assign the meal to the patient in the database");
				return false;
			}
		} catch (SQLException ex) {
			logger.error("Some error occured while assigning the meal to the patient in the database", ex);
			return false;
		}
		logger.info("The meal is assigned successfully to the patient in the database");
		return true;
	}

	@Override
	public boolean removeMeal(int patientId, int mealId) {
		try (PreparedStatement pstmt = connection.prepareStatement(SqlQueries.SQL_REMOVE_PATIENT_MEAL)) {
			int k = 1;
			pstmt.setInt(k++, patientId);
			pstmt.setInt(k++, mealId);
			if (pstmt.executeUpdate() == 0) {
				logger.info("Couldn't remove the meal from the patient in the database");
				return false;
			}
		} catch (SQLException ex) {
			logger.error("Some error occured while removing the meal from the patient in the database", ex);
			return false;
		}
		logger.info("The meal is removed successfully from the patient in the database");
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
				throw new IllegalStateException("Cannot close Autoclosable object" + ac);
			}
		}
	}

	private Page<Patient> extractPatientPage(ResultSet resultSet, String sort, int offset) throws SQLException {
		CategoryMapper categoryMapper = new CategoryMapper();
		DoctorMapper doctorMapper = new DoctorMapper();
		MealMapper mealMapper = new MealMapper();
		PatientMapper patientMapper = new PatientMapper();
		PrescriptionMapper prescriptionMapper = new PrescriptionMapper();
		UserMapper userMapper = new UserMapper();

		Map<Integer, User> users = new HashMap<>();
		Map<Integer, Category> categories = new HashMap<>();
		Map<Integer, Doctor> doctors = new HashMap<>();
		Map<Integer, Meal> meals = new HashMap<>();
		Map<Integer, Patient> patients = new LinkedHashMap<>();
		Map<Integer, Prescription> prescriptions = new HashMap<>();

		User user = null;
		Category category = null;
		Doctor doctor = null;
		Meal meal = null;
		Patient patient = null;
		Prescription prescription = null;

		int totalRows = -1;

		while (resultSet.next()) {
			if (totalRows == -1) {
				totalRows = resultSet.getInt("total_rows");
			}

			user = userMapper.extractFromResultSet(resultSet);
			category = categoryMapper.extractFromResultSet(resultSet);
			doctor = doctorMapper.extractFromResultSet(resultSet);

			user = userMapper.makeUnique(users, user);
			category = categoryMapper.makeUnique(categories, category);
			doctor = doctorMapper.makeUnique(doctors, doctor);

			doctor.setCategory(category);
			doctor.setUser(user);

			patient = patientMapper.extractFromResultSet(resultSet);
			patient = patientMapper.makeUnique(patients, patient);
			patient.setDoctor(doctor);

			meal = mealMapper.extractFromResultSet(resultSet);
			meal = mealMapper.makeUnique(meals, meal);
			if (meal.getId() > 0) {
				patient.getMeals().add(meal);				
			}

			prescription = prescriptionMapper.extractFromResultSet(resultSet);
			prescription = prescriptionMapper.makeUnique(prescriptions, prescription);
			prescription.setPatient(patient);

			if (!patient.getPrescriptions().contains(prescription) && prescription.getId() > 0) {
				patient.getPrescriptions().add(prescription);
			}
		}
		List<Patient> list = new ArrayList<>(patients.values());

		int newPageNumber = offset / Constants.PAGE_SIZE;
		int allPages = totalRows / Constants.PAGE_SIZE + 1;

		return new Page<Patient>(list, sort, newPageNumber, allPages);
	}

}
