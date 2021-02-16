package ua.hospital.servletapp.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Alexander-PC
 * 
 * This class is managing language locale switching.
 * The request parameter "lang" holds new value of locale that will be stored in the session context.
 *
 */

public class SessionLocaleFilter implements Filter {
	
	final static Logger logger = LogManager.getLogger(SessionLocaleFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getParameter("lang") != null) {
			req.getSession().setAttribute("lang", req.getParameter("lang"));
			logger.info("language locale is switched");
		}
		chain.doFilter(request, response);

	}

}
