package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.model.entity.Prescription;
import ua.hospital.servletapp.model.entity.Prescription.PrescriptionType;
import ua.hospital.servletapp.model.service.PrescriptionService;

public class PrescriptionCreateCommand implements Command {
	private final static Logger logger = LogManager.getLogger(PrescriptionCreateCommand.class);
	private PrescriptionService prescriptionService = new PrescriptionService();

	@Override
	public String execute(HttpServletRequest request) {
		String descriptionEn = request.getParameter("descriptionEn");
		String descriptionUk = request.getParameter("descriptionUk");
		String stringPrescriptionType = request.getParameter("prescriptionType");
		String stringPatientId = request.getParameter("patientId");
				
		if (descriptionEn == null || descriptionEn.equals("") || descriptionUk == null ||
				descriptionUk.equals("") || stringPrescriptionType == null) {
			
			logger.info("Invalid prescription input information");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		int patientId;
		PrescriptionType prescriptionType;
		
		try {
			patientId = Integer.parseInt(stringPatientId);
			prescriptionType = PrescriptionType.valueOf(stringPrescriptionType);
		} catch (IllegalArgumentException ex) {
			logger.error("Invalid data in prescription input information", ex);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		Prescription prescription = Prescription.builder()
				.withDescriptionEn(descriptionEn)
				.withDescriptionUk(descriptionUk)
				.withPrescriptionType(prescriptionType)
				.withPatient(new Patient(patientId))
				.build();
		
		if (prescriptionService.createPrescription(prescription)) {
			logger.info("New prescription is successfully created");
			return "redirect:/patient_get?patientId=" + patientId;
		}
		
		logger.info("Could not create new prescription");
		request.setAttribute("message", "invalidData");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
