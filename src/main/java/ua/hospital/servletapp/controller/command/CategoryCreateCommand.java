package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.model.service.CategoryService;
import ua.hospital.servletapp.support.ValidationHelper;

public class CategoryCreateCommand implements Command {
	private final static Logger logger = LogManager.getLogger(CategoryCreateCommand.class);
	private CategoryService categoryService = new CategoryService();

	@Override
	public String execute(HttpServletRequest request) {
		String nameEn = request.getParameter("nameEn");
		String nameUk = request.getParameter("nameUk");
				
		if (nameEn == null || !ValidationHelper.isLatinName(nameEn) || nameUk == null || 
				!ValidationHelper.isCyrillicName(nameUk)) {
			
			logger.info("Invalid category registration information");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		if (categoryService.createCategory(new Category(nameEn, nameUk))) {
			logger.info("new category is created successfully");
			return "redirect:/category_list";
		}
		
		logger.info("couldn't complete creation of the new category");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
