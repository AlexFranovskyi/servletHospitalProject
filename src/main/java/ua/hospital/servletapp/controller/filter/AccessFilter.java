package ua.hospital.servletapp.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.User;

/**
 * @author Alexander-PC
 * 
 * This filter class is checking if incoming request contains command in URI, permitted by current User Role.
 * During initialization, filter reads all commands in the deployment descriptor where they are stored as filter init-parameter
 * with User's role as <param-name>
 *
 */

public class AccessFilter implements Filter{
	
final static Logger logger = LogManager.getLogger(AccessFilter.class);
	
	private static Map<String, List<String>> commandMap;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String path = req.getRequestURI();
		path = path.replaceAll(".*/servletHospitalProject/", "");
		
		logger.info("User login: " + req.getSession().getAttribute("login"));
        logger.info("User role: " + req.getSession().getAttribute("role"));
		
		if ("".equals(path) || commandMap.get(session.getAttribute("role")).contains(path)) {
			chain.doFilter(request, response);
			logger.info("Access to command is allowed");
			return;
		}
		
		logger.info("Access to command is denied");
		res.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
		
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		commandMap = new HashMap<>();
		commandMap.put(User.Role.GUEST.toString(), toList(filterConfig.getInitParameter("guest")));
		commandMap.put(User.Role.NURSE.toString(), toList(filterConfig.getInitParameter("nurse")));
		commandMap.put(User.Role.DOCTOR.toString(), toList(filterConfig.getInitParameter("doctor")));
		commandMap.put(User.Role.ADMIN.toString(), toList(filterConfig.getInitParameter("admin")));
		
	}

	private List<String> toList(String str) {
		List<String> list = new ArrayList<>();
		String[] array = str.split(", ");
		for (String command : array) {
			list.add(command);
		}
		return list;
	}

}
