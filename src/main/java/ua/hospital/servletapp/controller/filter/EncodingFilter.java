package ua.hospital.servletapp.controller.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Alexander-PC
 *
 * This class sets encoding of servlet requests/responses to UTF-8, mainly to avoid problems with cyrillic characters.
 */

public class EncodingFilter implements Filter {
	
	final static Logger logger = LogManager.getLogger(EncodingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request, response);
        logger.info("Setting up encoding");

	}

}
