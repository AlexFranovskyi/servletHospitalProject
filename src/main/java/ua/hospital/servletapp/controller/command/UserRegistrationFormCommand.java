package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

public class UserRegistrationFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		return "WEB-INF/views/userRegistrationForm.jsp";
	}

}
