package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	String execute(HttpServletRequest request);
}
