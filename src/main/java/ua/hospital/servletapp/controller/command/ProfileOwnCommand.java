package ua.hospital.servletapp.controller.command;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.DoctorDto;
import ua.hospital.servletapp.model.dto.UserDto;
import ua.hospital.servletapp.model.entity.User.Role;
import ua.hospital.servletapp.model.service.DoctorService;
import ua.hospital.servletapp.model.service.UserService;

public class ProfileOwnCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ProfileOwnCommand.class);
	private UserService userService = new UserService();
	private DoctorService doctorService = new DoctorService();

	@Override
	public String execute(HttpServletRequest request) {
		
		String login = request.getSession().getAttribute("login").toString();
		Role role = Role.valueOf(request.getSession().getAttribute("role").toString());
		
		Optional<DoctorDto> optionalDoctor = Optional.empty();
		if (role == Role.DOCTOR) {
			optionalDoctor = doctorService.findByUserLogin(login);
		}
		if(optionalDoctor.isPresent()) {
			logger.info("Doctor is found and sent");
			logger.info( optionalDoctor.get());
			request.setAttribute("doctor", optionalDoctor.get());
			return "WEB-INF/views/profile.jsp";
		}
		
		Optional<UserDto> optionalUser = Optional.empty();
		if (role != Role.DOCTOR) {
			optionalUser = userService.findByLogin(login);
		}
		if(optionalUser.isPresent()) {
			logger.info("User is found and sent");
			logger.info( optionalUser.get());
			request.setAttribute("user", optionalUser.get());
			return "WEB-INF/views/profile.jsp";
		}
		
		logger.error("Some error occured while entity searching");
		request.setAttribute("message", "dataMissing");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
