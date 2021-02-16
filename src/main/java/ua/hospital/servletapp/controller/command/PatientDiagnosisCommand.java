package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.service.PatientService;
import ua.hospital.servletapp.support.ValidationHelper;

public class PatientDiagnosisCommand implements Command{
	private static final Logger logger = LogManager.getLogger(PatientDiagnosisCommand.class);
	private PatientService patientService = new PatientService();

	@Override
	public String execute(HttpServletRequest request) {
		String stringPatientId = request.getParameter("patientId");
		String diagnosisEn = request.getParameter("diagnosisEn");
		String diagnosisUk = request.getParameter("diagnosisUk");
		
		if (diagnosisEn == null || !ValidationHelper.isPhraseEn(diagnosisEn) ||
				diagnosisUk == null || !ValidationHelper.isPhraseUk(diagnosisUk)) {
			
			logger.error("Invalid diagnosis information");
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
				
		int patientId;
		
		try {
			patientId = Integer.parseUnsignedInt(stringPatientId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		if (patientService.defineDiagnosis(patientId, diagnosisEn, diagnosisUk)) {
			logger.info("The diagnosis is successfully defined to the Patient");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while diagnosis defining to a patient");
		request.setAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
