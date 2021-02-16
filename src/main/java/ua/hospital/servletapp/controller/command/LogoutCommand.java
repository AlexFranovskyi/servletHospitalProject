package ua.hospital.servletapp.controller.command;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ua.hospital.servletapp.model.entity.User;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String login = session.getAttribute("login").toString();
		
		HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext()
				.getAttribute("loggedUsers");
		loggedUsers.remove(login);
		session.getServletContext().setAttribute("loggedUsers", loggedUsers);
		
		LoginUtility.setUserLoginAndRole(request, User.Role.GUEST, "Guest");
		return "redirect:";
	}

}
