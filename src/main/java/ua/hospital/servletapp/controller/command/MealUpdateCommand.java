package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Meal;
import ua.hospital.servletapp.model.service.MealService;
import ua.hospital.servletapp.support.ValidationHelper;

public class MealUpdateCommand implements Command {
	private final static Logger logger = LogManager.getLogger(MealUpdateCommand.class);
	private MealService mealService = new MealService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringMealId = request.getParameter("mealId");
		String nameEn = request.getParameter("nameEn");
		String nameUk = request.getParameter("nameUk");
		
		if (stringMealId == null || nameEn == null || !ValidationHelper.isLatinName(nameEn) || 
				nameUk == null || !ValidationHelper.isCyrillicName(nameUk)) {
			
			logger.info("Invalid meal updating information");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		int mealId;
		
		try {
			mealId = Integer.parseInt(stringMealId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if(mealService.updateMeal(new Meal(mealId, nameEn, nameUk))) {
			logger.info("The meal is successfully updated");
			return "redirect:/meal_list";
		}
		
		logger.error("Some error occured while meal updating");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
