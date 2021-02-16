package ua.hospital.servletapp.controller.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.service.PatientService;
import ua.hospital.servletapp.support.Constants;
import ua.hospital.servletapp.support.ValidationHelper;

public class PatientRegisterCommand implements Command {
	private static final Logger logger = LogManager.getLogger(PatientRegisterCommand.class);
	private PatientService patientService = new PatientService();

	@Override
	public String execute(HttpServletRequest request) {
		String firstNameEn = request.getParameter("firstNameEn");
		String firstNameUk = request.getParameter("firstNameUk");
		String lastNameEn = request.getParameter("lastNameEn");
		String lastNameUk = request.getParameter("lastNameUk");
	
		String stringBirthDate = request.getParameter("birthDate");
		
		if (firstNameEn == null || !ValidationHelper.isLatinName(firstNameEn) ||
				firstNameUk == null || !ValidationHelper.isCyrillicName(firstNameUk) ||
				lastNameEn == null || !ValidationHelper.isLatinName(lastNameEn) ||
				lastNameUk == null || !ValidationHelper.isCyrillicName(lastNameUk) ||
				stringBirthDate == null) {
			
			logger.info("invalid patient registration information");
			
			request.setAttribute("message", "invalidData");
			return "WEB-INF/views/patientRegistrationForm.jsp";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.LOCAL_DATE_PATTERN);
		LocalDate localDateBirth = null;
		
		try {
			localDateBirth = LocalDate.parse(stringBirthDate, formatter);
		} catch (DateTimeParseException e) {
			logger.error("invalid patient registration information", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/views/patientRegistrationForm.jsp";
		}
		
		Person person = Person.builder()
				.withFirstNameEn(firstNameEn)
				.withFirstNameUk(firstNameUk)
				.withLastNameEn(lastNameEn)
				.withLastNameUk(lastNameUk)
				.withBirthDate(localDateBirth)
				.build();
		
		Patient patient = Patient.builder()
				.withPerson(person)
				.build();
		
		if (patientService.createPatient(patient)) {
			logger.info("New patient is registered successfully");
			return "redirect:";
		}
		logger.info("Couldn't register new patient");
		request.setAttribute("message", "patientRegistrationFailed");
		return "WEB-INF/views/patientRegistrationForm.jsp";
	}

}
