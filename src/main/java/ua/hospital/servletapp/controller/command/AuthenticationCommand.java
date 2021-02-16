package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		return "WEB-INF/views/loginForm.jsp";
	}

}
