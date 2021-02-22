package ua.hospital.servletapp.controller.command;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.PatientDto;
import ua.hospital.servletapp.model.service.PatientService;

public class PatientGetCommand implements Command {
	private static final Logger logger = LogManager.getLogger(PatientGetCommand.class);
	private PatientService patientService = new PatientService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");
		int patientId;
		
		try {
			patientId = Integer.parseUnsignedInt(stringPatientId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		Optional<PatientDto> optional = patientService.findById(patientId);
		
		if(optional.isPresent()) {
			logger.info("Patient is found and sent");
			request.setAttribute("patient", optional.get());
			return "WEB-INF/views/medicalCard.jsp";
		}
		
		logger.error("Some error occured while patient entity searching");
		request.setAttribute("message", "dataMissing");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
