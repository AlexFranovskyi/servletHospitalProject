package ua.hospital.servletapp.support;

public class SqlQueries {
	
	public static final String SQL_INSERT_PERSON ="INSERT INTO person "
			+ "(first_name_en, first_name_uk, last_name_en, last_name_uk, birth_date) values (?, ?, ?, ?, ?)";
	
	public static final String SQL_INSERT_USER = "INSERT INTO users (person_id, role, password, login) values (?, ?, ?, ?)";
	public static final String SQL_INSERT_DOCTOR = "INSERT INTO doctor(user_id) values (?)";
	
	
	public static final String SQL_USER_FIND_ALL = "SELECT (SELECT count(distinct users.id) FROM users) as total_rows, u.id, role, "
			+ "password, login, up.id, up.first_name_en, up.first_name_uk, up.last_name_en, up.last_name_uk, up.birth_date "
			+ "FROM users as u LEFT JOIN person as up ON u.person_id = up.id";
	
	public static final String SQL_USER_FIND_BY_ID = SQL_USER_FIND_ALL + " WHERE u.id = ?";
	public static final String SQL_USER_FIND_BY_LOGIN = SQL_USER_FIND_ALL + " WHERE login=?";
	public static final String SQL_USER_FIND_ALL_PAGINATED = SQL_USER_FIND_ALL + " ORDER BY %s LIMIT ? OFFSET ?";
	
	public static final String SQL_INSERT_CATEGORY = "INSERT INTO category (name_en, name_uk) values (?, ?)";
	
	public static final String SQL_ASSIGN_CATEGORY = "UPDATE doctor SET category_id = ? WHERE id = ?";
	public static final String SQL_UPDATE_CATEGORY = "UPDATE category SET name_en = ?, name_uk = ? WHERE id = ?";
	public static final String SQL_DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";
	public static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM category as c";
	
	public static final String SQL_INSERT_PATIENT = "INSERT INTO patient (person_id, arrive_date) values (?, now())";
	public static final String SQL_ASSIGN_DOCTOR = "UPDATE patient SET doctor_id = ? WHERE id = ?";
	public static final String SQL_DEFINE_DIAGNOSIS = "UPDATE patient SET diagnosis_en = ?, diagnosis_uk = ? WHERE id = ?";
	public static final String SQL_DISCHARGE_PATIENT = "UPDATE patient SET discharge_date_time = NOW() WHERE id = ?";
	public static final String SQL_ASSIGN_PATIENT_MEAL = "INSERT INTO patient_has_meal (patient_id, meal_id) values (?, ?)";
	public static final String SQL_REMOVE_PATIENT_MEAL = "DELETE FROM patient_has_meal WHERE patient_id = ? AND meal_id = ?";
	
	public static final String SQL_INSERT_PRESCRIPTION = "INSERT INTO prescription (description_en, description_uk,"
			+ "prescription_type, patient_id) values (?, ?, ?, ?)";
	public static final String SQL_COMPLETE_PRESCRIPTION = "UPDATE prescription SET completion_time = NOW() WHERE id = ?";
	public static final String SQL_DELETE_PRESCRIPTION = "DELETE FROM prescription WHERE id = ?";
	public static final String SQL_DELETE_NOT_NEEDED_PRESCRIPTIONS_BY_PATIENT = "DELETE FROM prescription "
			+ "WHERE completion_time IS NULL AND patient_id = ?";
	
	public static final String SQL_INSERT_MEAL = "INSERT INTO meal (name_en, name_uk) values (?, ?)";
	public static final String SQL_UPDATE_MEAL = "UPDATE meal SET name_en = ?, name_uk = ? WHERE id = ?";
	public static final String SQL_DELETE_MEAL = "DELETE FROM meal WHERE id = ?";
	public static final String SQL_FIND_ALL_MEALS = "SELECT * FROM meal as m";
	public static final String SQL_PATIENT_FIND_ALL = "SELECT p.id, arrive_date, diagnosis_en, diagnosis_uk, discharge_date_time, "
			+ "pp.id, pp.first_name_en, pp.first_name_uk, pp.last_name_en, pp.last_name_uk, pp.birth_date, d.id, "
			+ "(SELECT count(distinct id) FROM patient WHERE doctor_id = d.id and discharge_date_time IS NULL) as patient_amount, "
			+ "c.id, c.name_en, c.name_uk, u.id, role, password, login, up.id, up.first_name_en, up.first_name_uk, up.last_name_en, "
			+ "up.last_name_uk, up.birth_date, pr.id, description_en, description_uk, prescription_type, completion_time, m.id, "
			+ "m.name_en, m.name_uk FROM patient as p "
			+ "LEFT JOIN person as pp on p.person_id = pp.id LEFT JOIN doctor as d ON p.doctor_id = d.id LEFT JOIN category as c on "
			+ "d.category_id = c.id LEFT JOIN users as u ON d.user_id = u.id LEFT JOIN person as up ON u.person_id = up.id "
			+ "LEFT JOIN prescription as pr ON p.id = pr.patient_id LEFT JOIN patient_has_meal as phm ON p.id = phm.patient_id "
			+ "LEFT JOIN meal as m ON m.id = phm.meal_id";
	
	public static final String SQL_PATIENT_FIND_BY_ID = SQL_PATIENT_FIND_ALL + " where p.id = ?";
	
	public static final String SQL_PATIENT_FIND_ALL_PAGINATED = "SELECT (select count(distinct patient.id) FROM patient) as total_rows, "
			+ "p.id, arrive_date, diagnosis_en, diagnosis_uk, "
			+ "discharge_date_time, pp.id, pp.first_name_en, pp.first_name_uk, pp.last_name_en, pp.last_name_uk, pp.birth_date, d.id, "
			+ "(SELECT count(distinct id) FROM patient WHERE doctor_id = d.id and discharge_date_time IS NULL) as patient_amount, "
			+ "c.id, c.name_en, c.name_uk, u.id, role, password, login, up.id, up.first_name_en, up.first_name_uk, up.last_name_en, "
			+ "up.last_name_uk, up.birth_date, pr.id, description_en, description_uk, prescription_type, completion_time, m.id, "
			+ "m.name_en, m.name_uk FROM patient as p "
			+ "LEFT JOIN person as pp on p.person_id = pp.id LEFT JOIN doctor as d ON p.doctor_id = d.id LEFT JOIN category as c on "
			+ "d.category_id = c.id LEFT JOIN users as u ON d.user_id = u.id LEFT JOIN person as up ON u.person_id = up.id "
			+ "LEFT JOIN prescription as pr ON p.id = pr.patient_id LEFT JOIN patient_has_meal as phm ON p.id = phm.patient_id "
			+ "LEFT JOIN meal as m ON m.id = phm.meal_id  INNER JOIN (SELECT patient.id from patient "
			+ "LEFT JOIN person ON patient.person_id = person.id ORDER BY %s LIMIT ? OFFSET ?) as page_table "
			+ "ON page_table.id = p.id";
	
	public static final String SQL_PATIENT_FIND_PAGINATED_BY_DOCTOR_ID = "SELECT (select count(distinct patient.id) FROM patient "
			+ "LEFT JOIN doctor ON patient.doctor_id = doctor.id WHERE doctor.id = ?) as total_rows, "
			+ "p.id, arrive_date, diagnosis_en, diagnosis_uk, discharge_date_time, pp.id, pp.first_name_en, pp.first_name_uk, "
			+ "pp.last_name_en, pp.last_name_uk, pp.birth_date, d.id, "
			+ "(SELECT count(distinct id) FROM patient WHERE doctor_id = d.id and discharge_date_time IS NULL) as patient_amount, "
			+ "c.id, c.name_en, c.name_uk, u.id, role, password, login, up.id, up.first_name_en, up.first_name_uk, up.last_name_en, "
			+ "up.last_name_uk, up.birth_date, pr.id, description_en, description_uk, prescription_type, completion_time, m.id, "
			+ "m.name_en, m.name_uk FROM patient as p "
			+ "LEFT JOIN person as pp on p.person_id = pp.id LEFT JOIN doctor as d ON p.doctor_id = d.id LEFT JOIN category as c on "
			+ "d.category_id = c.id LEFT JOIN users as u ON d.user_id = u.id LEFT JOIN person as up ON u.person_id = up.id "
			+ "LEFT JOIN prescription as pr ON p.id = pr.patient_id LEFT JOIN patient_has_meal as phm ON p.id = phm.patient_id "
			+ "LEFT JOIN meal as m ON m.id = phm.meal_id INNER JOIN "
			+ "(SELECT patient.id from patient LEFT JOIN person ON patient.person_id = person.id WHERE discharge_date_time IS NULL "
			+ "AND patient.doctor_id = ? ORDER BY %s LIMIT ? OFFSET ?) as page_table ON page_table.id = p.id";
	
	public static final String SQL_PATIENT_FIND_PAGINATED_BY_DOCTOR_USER_LOGIN = "SELECT "
			+ "(select count(distinct patient.id) FROM patient LEFT JOIN doctor ON patient.doctor_id = doctor.id "
			+ "LEFT JOIN users ON doctor.user_id = users.id WHERE users.id = ?) as total_rows, "
			+ "p.id, arrive_date, diagnosis_en, diagnosis_uk, discharge_date_time, "
			+ "pp.id, pp.first_name_en, pp.first_name_uk, pp.last_name_en, pp.last_name_uk, pp.birth_date, d.id, "
			+ "(SELECT count(distinct id) FROM patient WHERE doctor_id = d.id and discharge_date_time IS NULL) as patient_amount, "
			+ "c.id, c.name_en, c.name_uk, u.id, role, password, login, up.id, up.first_name_en, up.first_name_uk, up.last_name_en, "
			+ "up.last_name_uk, up.birth_date, pr.id, description_en, description_uk, prescription_type, completion_time, m.id, "
			+ "m.name_en, m.name_uk FROM patient as p "
			+ "LEFT JOIN person as pp on p.person_id = pp.id LEFT JOIN doctor as d ON p.doctor_id = d.id LEFT JOIN category as c on "
			+ "d.category_id = c.id LEFT JOIN users as u ON d.user_id = u.id LEFT JOIN person as up ON u.person_id = up.id "
			+ "LEFT JOIN prescription as pr ON p.id = pr.patient_id LEFT JOIN patient_has_meal as phm ON p.id = phm.patient_id "
			+ "LEFT JOIN meal as m ON m.id = phm.meal_id INNER JOIN "
			+ "(SELECT patient.id from patient LEFT JOIN person ON patient.person_id = person.id LEFT JOIN doctor ON "
			+ "patient.doctor_id = doctor.id LEFT JOIN users ON doctor.user_id = users.id WHERE WHERE discharge_date_time IS NULL AND "
			+ "users.login = ? ORDER BY %s LIMIT ? OFFSET ?) as page_table ON page_table.id = p.id";
	
	public static final String SQL_DOCTOR_FIND_ALL = "SELECT (SELECT count(distinct doctor.id) FROM doctor) as total_rows, d.id, "
			+ "c.id, c.name_en, c.name_uk, u.id, role, password, login, up.id, up.first_name_en, up.first_name_uk, up.last_name_en, "
			+ "up.last_name_uk, up.birth_date, "
			+ "(SELECT count(distinct id) FROM patient WHERE doctor_id = d.id AND discharge_date_time IS NULL) as patient_amount "
			+ "FROM doctor AS d LEFT JOIN category as c on d.category_id = c.id LEFT JOIN users as u ON d.user_id = u.id "
			+ "LEFT JOIN person as up ON u.person_id = up.id";
	
	public static final String SQL_DOCTOR_FIND_BY_ID = SQL_DOCTOR_FIND_ALL + " where d.id = ?";
	public static final String SQL_DOCTOR_FIND_BY_USER_LOGIN = SQL_DOCTOR_FIND_ALL + " where login = ?";
	public static final String SQL_DOCTOR_FIND_ALL_PAGINATED = SQL_DOCTOR_FIND_ALL + " ORDER BY %s LIMIT ? OFFSET ?";

}
