package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.MealService;

public class MealDeleteCommand implements Command {
	private final static Logger logger = LogManager.getLogger(MealDeleteCommand.class);
	private MealService mealService = new MealService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringMealId = request.getParameter("mealId");
		int mealId;

		try {
			mealId = Integer.parseInt(stringMealId);
		} catch (NumberFormatException e) {
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if(mealService.deleteMeal(mealId)) {
			logger.info("The meal is successfully deleted");
			return "redirect:/meal_list";
		}
		
		logger.error("Some error occured while meal deleting");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
