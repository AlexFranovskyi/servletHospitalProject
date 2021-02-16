package ua.hospital.servletapp.controller.listener;

import java.util.HashSet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.User;
import ua.hospital.servletapp.support.Constants;

/**
 * @author Alexander-PC
 *
 * This class adjusts starting attributes when user's session is created.
 * When session is destroyed SessionListener provides removing user's name 
 * from "loggedUsers" <tt>HashSet</tt>, stored in the ServletContext.
 */

public class SessionListener implements HttpSessionListener {
	
	final static Logger logger = LogManager.getLogger(SessionListener.class);
	
	@Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    	HttpSession session = httpSessionEvent.getSession();
    	session.setAttribute("lang", Constants.DEFAULT_LOCALE);
    	session.setAttribute("login", "Guest");
		session.setAttribute("role", User.Role.GUEST.toString());
		
		logger.info("New session is created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        
        String login = (String) httpSessionEvent.getSession()
                .getAttribute("login");
        loggedUsers.remove(login);
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
        
        logger.info("User session is destroyed");
    }

}
