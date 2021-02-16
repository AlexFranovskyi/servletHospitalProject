package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.PatientDto;
import ua.hospital.servletapp.model.service.PatientService;
import ua.hospital.servletapp.support.Page;

public class PatientListCommand implements Command {
	private PatientService patientService = new PatientService();
	private final static Logger logger = LogManager.getLogger(PatientListCommand.class); 

	@Override
	public String execute(HttpServletRequest request) {
		String sort = request.getParameter("sort");
		String stringPageNumber = request.getParameter("pageNumber");
		
		if(sort == null) {
			sort = "last_name";
		}
		int pageNumber;
		try {
			pageNumber = Integer.parseUnsignedInt(stringPageNumber);
		} catch (NumberFormatException e) {
			pageNumber = 0;
		}
		
		String locale = request.getSession().getAttribute("lang").toString();
		Page<PatientDto> page = patientService.findAllPaginated(pageNumber, sort, locale);
		logger.info(page);
		
		request.setAttribute("page", page);
		return "WEB-INF/views/patientList.jsp";
	}

}
