package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.PrescriptionService;

public class PrescriptionDeleteCommand implements Command {
	private static final Logger logger = LogManager.getLogger(PrescriptionDeleteCommand.class);
	private PrescriptionService prescriptionService = new PrescriptionService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");
		String stringPrescriptionId = request.getParameter("prescriptionId");
		int patientId;
		int prescriptionId;

		try {
			patientId = Integer.parseInt(stringPatientId);
			prescriptionId = Integer.parseInt(stringPrescriptionId);
		} catch (NumberFormatException e) {
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		if (prescriptionService.deletePrescription(prescriptionId)) {
			logger.info("The prescription is deleted successfully");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while deleting the prescription");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
