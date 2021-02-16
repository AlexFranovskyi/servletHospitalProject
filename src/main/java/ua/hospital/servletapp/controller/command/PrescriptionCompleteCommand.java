package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Prescription.PrescriptionType;
import ua.hospital.servletapp.model.entity.User.Role;
import ua.hospital.servletapp.model.service.PrescriptionService;

public class PrescriptionCompleteCommand implements Command {
	private final static Logger logger = LogManager.getLogger(PrescriptionCompleteCommand.class);
	private PrescriptionService prescriptionService = new PrescriptionService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");
		String stringPrescriptionId = request.getParameter("prescriptionId");
		String stringPrescriptionType = request.getParameter("prescriptionType");
		
		int patientId;
		int prescriptionId;
		PrescriptionType prescriptionType;

		try {
			patientId = Integer.parseUnsignedInt(stringPatientId);
			prescriptionId = Integer.parseUnsignedInt(stringPrescriptionId);
			prescriptionType = PrescriptionType.valueOf(stringPrescriptionType);
		} catch (NumberFormatException e) {
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		Role userRole = Role.valueOf(request.getSession().getAttribute("role").toString());
		
		if (userRole == Role.NURSE && prescriptionType == PrescriptionType.OPERATION) {
			logger.error("Attempt to complete the prescription by inappropriate user");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}

		if (prescriptionService.completePrescription(prescriptionId)) {
			logger.info("The prescription is completed successfully");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		logger.error("Some error occured while completing the prescription");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
