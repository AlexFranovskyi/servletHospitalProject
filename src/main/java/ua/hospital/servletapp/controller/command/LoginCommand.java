package ua.hospital.servletapp.controller.command;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.UserDto;
import ua.hospital.servletapp.model.service.UserService;
import ua.hospital.servletapp.support.ValidationHelper;

public class LoginCommand implements Command {

	private final static Logger logger = LogManager.getLogger(LoginCommand.class);
	private UserService userService = new UserService();

	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
	
		if (login == null || !ValidationHelper.isLogin(login) || password == null || 
				!ValidationHelper.isPassword(password)) {
			
			logger.info("invalid user authorization information");

			request.setAttribute("message", "invalidData");
			return "WEB-INF/views/loginForm.jsp";
		}
		
		
		Optional<UserDto> optional = userService.authorizeByLoginAndPassword(login, password);
				
		if (!optional.isPresent()) {
			logger.info("Incorrect login or password");
			request.setAttribute("message", "incorrectLoginPass");
			return "WEB-INF/views/loginForm.jsp";
		}
		
		if (LoginUtility.checkUserIsLogged(request, login)) {
			logger.info("This user is already logged in the system");
			request.setAttribute("message", "userIsAlreadyLogged");
			return "WEB-INF/views/loginForm.jsp";
		}
		
		LoginUtility.setUserLoginAndRole(request, optional.get().getRole(), optional.get().getLogin());
		logger.info("User is logged into the system");
		return "redirect:";
	}

}
