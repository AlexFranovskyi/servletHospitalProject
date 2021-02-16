package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.CategoryService;

public class CategoryDeleteCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CategoryDeleteCommand.class);
	private CategoryService categoryService = new CategoryService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringCategoryId = request.getParameter("categoryId");
		int categoryId;
		
		try {
			categoryId = Integer.parseInt(stringCategoryId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if(categoryService.deleteCategory(categoryId)) {
			logger.info("Category is successfully deleted");
			return "redirect:/category_list";
		}
		
		logger.error("Some error occured while category deleting");
		request.setAttribute("message", "unableComplete");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
