package ua.hospital.servletapp.controller.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Doctor;
import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.entity.User;
import ua.hospital.servletapp.model.service.DoctorService;
import ua.hospital.servletapp.model.service.UserService;
import ua.hospital.servletapp.support.Constants;
import ua.hospital.servletapp.support.ValidationHelper;

public class UserRegisterCommand implements Command {
	private final static Logger logger = LogManager.getLogger(UserRegisterCommand.class);
	
	private UserService userService = new UserService();
	private DoctorService doctorService = new DoctorService();

	@Override
	public String execute(HttpServletRequest request) {
		String firstNameEn = request.getParameter("firstNameEn");
		String firstNameUk = request.getParameter("firstNameUk");
		String lastNameEn = request.getParameter("lastNameEn");
		String lastNameUk = request.getParameter("lastNameUk");
	
		String stringBirthDate = request.getParameter("birthDate");	
		String stringRole = request.getParameter("role");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		if (firstNameEn == null || !ValidationHelper.isLatinName(firstNameEn) ||
			firstNameUk == null || !ValidationHelper.isCyrillicName(firstNameUk) ||
			lastNameEn == null || !ValidationHelper.isLatinName(lastNameEn) ||
			lastNameUk == null || !ValidationHelper.isCyrillicName(lastNameUk) ||
			
			stringBirthDate == null || stringRole == null ||
			
			login == null || !ValidationHelper.isLogin(login) ||
			password == null || !ValidationHelper.isPassword(password)) {
			
			logger.info("invalid user registration information");
			
			request.setAttribute("message", "invalidData");
			return "WEB-INF/views/userRegistrationForm.jsp";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.LOCAL_DATE_PATTERN);
		LocalDate localDateBirth = null;
		User.Role role = null;
		
		try {
			localDateBirth = LocalDate.parse(stringBirthDate, formatter);
			role = User.Role.valueOf(stringRole);
		} catch (DateTimeParseException | IllegalArgumentException ex) {
			logger.error("Invalid data in user's input registration information", ex);
			request.setAttribute("message", "invalidData");
            return "WEB-INF/views/userRegistrationForm.jsp";
		}
				
		Person person = Person.builder()
				.withFirstNameEn(firstNameEn)
				.withFirstNameUk(firstNameUk)
				.withLastNameEn(lastNameEn)
				.withLastNameUk(lastNameUk)
				.withBirthDate(localDateBirth)
				.build();
		
		User user = User.builder()
				.withRole(role)
				.withPerson(person)
				.withLogin(login)
				.withPassword(password)
				.build();
				
		if (createAccordingToRole(user)) {
			logger.info("New account is successfully created");
			return "redirect:";
		}
		logger.info("Could not create new account");
		request.setAttribute("message", "userRegistrationFailed");
		return "WEB-INF/views/userRegistrationForm.jsp";
	}
	
	/**
	 * Method that helps to choose how to process the {@link User} object
	 * regarding its {@link User#getRole()} value.
	 * 
	 * @param user - the User object to process by deeper methods
	 * @return - value returned by deeper methods.
	 */
	
	private boolean createAccordingToRole(User user) {
		if (user.getRole() == User.Role.DOCTOR) {
			return doctorService.createDoctor(new Doctor(user));
		}
		return userService.createUser(user);
	}

}
