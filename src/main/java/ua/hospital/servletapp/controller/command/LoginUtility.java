package ua.hospital.servletapp.controller.command;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ua.hospital.servletapp.model.entity.User;

/**
 * @author Alexander-PC
 * 
 * This is a helper class. Its main purpose is to protect web application from double loginning of the user. 
 *
 */
public class LoginUtility {
	
	static void setUserLoginAndRole(HttpServletRequest request, User.Role role, String login) {
		HttpSession session = request.getSession();
		session.setAttribute("login", login);
		session.setAttribute("role", role.toString());

	}
	
	/**
	 * Calling this method allows to check if the user is logged into <tt>HashSet</tt> "loggedUsers",
	 * stored in the ServletContext. If not, method adds new user's name to <tt>HashSet</tt> "loggedUsers" storage.
	 * 
	 * @param request an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet
     *                  
	 * @param username a {@link String} object containing the name
	 * 					of the user
	 * 
	 */

	static boolean checkUserIsLogged(HttpServletRequest request, String login) {
		HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
				.getAttribute("loggedUsers");

		if (loggedUsers.stream().noneMatch(login::equals)) {
			synchronized (LoginUtility.class) {
				if (loggedUsers.stream().noneMatch(login::equals)) {
					loggedUsers.add(login);
					request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
					return false;
				}
			}
		}
		return true;
	}

}
