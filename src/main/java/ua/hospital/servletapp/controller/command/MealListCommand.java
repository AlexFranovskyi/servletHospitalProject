package ua.hospital.servletapp.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.MealDto;
import ua.hospital.servletapp.model.service.MealService;

public class MealListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(MealListCommand.class);
	private MealService mealService = new MealService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");

		if (stringPatientId != null) {
			try {
				int patientId = Integer.parseUnsignedInt(stringPatientId);
				request.setAttribute("patientId", patientId);
				request.setAttribute("message", "mealAssigning");
				logger.info("List is formed to assign a meal");
			} catch (NumberFormatException e) {
				logger.error("Wrong meal Id format is sent before select a meal");
				request.setAttribute("message", "invalidData");
				return "WEB-INF/errors/errorMessage.jsp";
			}
		}

		List<MealDto> list = mealService.findAll();
		request.setAttribute("mealList", list);
		logger.info("List of meals is send");
		return "WEB-INF/views/mealList.jsp";
	}

}
