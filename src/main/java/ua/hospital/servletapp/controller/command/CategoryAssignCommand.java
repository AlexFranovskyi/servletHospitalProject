package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.DoctorService;

public class CategoryAssignCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CategoryAssignCommand.class);
	private DoctorService doctorService = new DoctorService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringDoctorId = request.getParameter("doctorId");
		String stringCategoryId = request.getParameter("categoryId");
		
		int doctorId;
		int categoryId;
		
		try {
			doctorId = Integer.parseInt(stringDoctorId);
			categoryId = Integer.parseInt(stringCategoryId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		if (doctorService.assignCategory(doctorId, categoryId)) {
			logger.info("Category is successfully assigned to Doctor");
			return new StringBuilder("redirect:/profile?userId=").append(doctorId)
					.append("&userRole=doctor").toString();
		}
		
		logger.error("Some error occured while category assigning to doctor");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
