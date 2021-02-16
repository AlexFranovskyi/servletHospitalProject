package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.PatientDto;
import ua.hospital.servletapp.model.service.PatientService;
import ua.hospital.servletapp.support.Page;

public class DoctorPatientsListCommand implements Command {
	
	private PatientService patientService = new PatientService();
	private final static Logger logger = LogManager.getLogger(DoctorPatientsListCommand.class);

	@Override
	public String execute(HttpServletRequest request) {
		String stringDoctorId = request.getParameter("doctorId");
		String sort = request.getParameter("sort");
		String stringPageNumber = request.getParameter("pageNumber");
		
		if(sort == null) {
			sort = "last_name";
		}
		int doctorId;
		int pageNumber;
		
		try {
			doctorId = Integer.parseUnsignedInt(stringDoctorId);
		} catch (NumberFormatException e){
			logger.error("Invalid input data", e);
			request.setAttribute("message", "invalidData");
			return "WEB-INF/errors/errorMessage.jsp";
		}
		
		try {
			pageNumber = Integer.parseUnsignedInt(stringPageNumber);
		} catch (NumberFormatException e) {
			pageNumber = 0;
		}
		
		String locale = request.getSession().getAttribute("lang").toString();
		Page<PatientDto> page = patientService.findByDoctorIdPaginated(pageNumber, sort, locale, doctorId);
		logger.info(page);
		request.setAttribute("message", "doctorPatientsList");
		request.setAttribute("doctorId", doctorId);
		request.setAttribute("page", page);
		return "WEB-INF/views/patientList.jsp";
	}

}
