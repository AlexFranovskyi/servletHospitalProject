package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

public class PatientRegistrationFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		return "WEB-INF/views/patientRegistrationForm.jsp";
	}

}
