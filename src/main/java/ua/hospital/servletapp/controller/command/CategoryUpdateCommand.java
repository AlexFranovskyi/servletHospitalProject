package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.model.service.CategoryService;
import ua.hospital.servletapp.support.ValidationHelper;

public class CategoryUpdateCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CategoryUpdateCommand.class);
	private CategoryService categoryService = new CategoryService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringCategoryId = request.getParameter("categoryId");
		String nameEn = request.getParameter("nameEn");
		String nameUk = request.getParameter("nameUk");
		
		if (stringCategoryId == null || nameEn == null || !ValidationHelper.isLatinName(nameEn) || 
				nameUk == null || !ValidationHelper.isCyrillicName(nameUk)) {
			
			logger.info("Invalid category updating information");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		int categoryId;
		
		try {
			categoryId = Integer.parseInt(stringCategoryId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if(categoryService.updateCategory(new Category(categoryId, nameEn, nameUk))) {
			logger.info("Category is successfully updated");
			return "redirect:/category_list";
		}
		
		logger.error("Some error occured while category updating");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
