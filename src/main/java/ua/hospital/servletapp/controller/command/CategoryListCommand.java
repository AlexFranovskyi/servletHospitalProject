package ua.hospital.servletapp.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.CategoryDto;
import ua.hospital.servletapp.model.service.CategoryService;

public class CategoryListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CategoryListCommand.class);
	private CategoryService categoryService = new CategoryService();
		
	@Override
	public String execute(HttpServletRequest request) {
		String stringDoctorId = request.getParameter("doctorId");
		
		if (stringDoctorId != null) {
			try {
				int doctorId = Integer.parseUnsignedInt(stringDoctorId);
				request.setAttribute("doctorId", doctorId);
				request.setAttribute("message", "categoryAssigning");
				logger.info("List is formed to assign a category");
			} catch (NumberFormatException e) {
				logger.error("Wrong doctor Id format is sent before select a category");
				request.setAttribute("message", "invalidData");
				return "WEB-INF/errors/errorMessage.jsp";
			}
		}
		
		List<CategoryDto> list = categoryService.findAll();
		request.setAttribute("categoryList", list);
		logger.info("List of categories is send");
		return "WEB-INF/views/categoryList.jsp";
	}

}
