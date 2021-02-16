package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.PatientService;

public class PatientDischargeCommand implements Command {
	private static final Logger logger = LogManager.getLogger(PatientDischargeCommand.class);
	private PatientService patientService = new PatientService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");
		int patientId;

		try {
			patientId = Integer.parseInt(stringPatientId);
		} catch (NumberFormatException e) {
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if (patientService.dischargePatient(patientId)) {
			logger.info("The patient is discharged successfully");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		logger.error("Some error occured while discharging the patient");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
