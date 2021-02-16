package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.PatientService;

public class PatientMealRemoveCommand implements Command {
	private static final Logger logger = LogManager.getLogger(PatientMealRemoveCommand.class);
	private PatientService patientService = new PatientService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");
		String stringMealId = request.getParameter("mealId");
				
		int patientId;
		int mealId;
		
		try {
			patientId = Integer.parseInt(stringPatientId);
			mealId = Integer.parseInt(stringMealId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if (patientService.removeMeal(patientId, mealId)) {
			logger.info("The meal is removed successfully from the patient");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}

		logger.error("Some error occured while meal removing from the patient");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
