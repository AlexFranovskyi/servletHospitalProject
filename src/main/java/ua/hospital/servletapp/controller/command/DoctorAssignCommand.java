package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.PatientService;

public class DoctorAssignCommand implements Command {
	private static final Logger logger = LogManager.getLogger(DoctorAssignCommand.class);
	private PatientService patientService = new PatientService();
	

	@Override
	public String execute(HttpServletRequest request) {
		String stringDoctorId = request.getParameter("doctorId");
		String stringPatientId = request.getParameter("patientId");
				
		int doctorId;
		int patientId;
		
		try {
			doctorId = Integer.parseInt(stringDoctorId);
			patientId = Integer.parseInt(stringPatientId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		if (patientService.assignDoctor(patientId, doctorId)) {
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while doctor assigning to patient");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
