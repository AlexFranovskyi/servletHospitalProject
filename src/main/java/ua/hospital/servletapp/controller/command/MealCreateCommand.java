package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Meal;
import ua.hospital.servletapp.model.service.MealService;
import ua.hospital.servletapp.support.ValidationHelper;

public class MealCreateCommand implements Command {
	private final static Logger logger = LogManager.getLogger(MealCreateCommand.class);
	private MealService mealService = new MealService();

	@Override
	public String execute(HttpServletRequest request) {
		String nameEn = request.getParameter("nameEn");
		String nameUk = request.getParameter("nameUk");
				
		if (nameEn == null || !ValidationHelper.isLatinName(nameEn) || nameUk == null || 
				!ValidationHelper.isCyrillicName(nameUk)) {
			
			logger.info("Invalid meal input data");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if (mealService.createMeal(new Meal(nameEn, nameUk))) {
			logger.info("new meal is created successfully");
			return "redirect:/meal_list";
		}

		logger.info("couldn't complete creation of the new meal");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
