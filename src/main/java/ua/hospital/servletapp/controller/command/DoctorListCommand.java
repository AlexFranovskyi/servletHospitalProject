package ua.hospital.servletapp.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.model.dto.DoctorDto;
import ua.hospital.servletapp.model.service.DoctorService;
import ua.hospital.servletapp.support.Page;

public class DoctorListCommand implements Command {
	private DoctorService doctorService = new DoctorService();
	private final static Logger logger = LogManager.getLogger(DoctorListCommand.class);

	@Override
	public String execute(HttpServletRequest request) {		
		String sort = request.getParameter("sort");
		String stringPageNumber = request.getParameter("pageNumber");
		String stringPatientId = request.getParameter("patientId");
		
		if(sort == null) {
			sort = "last_name";
		}
		int pageNumber;
		try {
			pageNumber = Integer.parseUnsignedInt(stringPageNumber);
		} catch (NumberFormatException e) {
			pageNumber = 0;
		}
		
		if (stringPatientId != null) {
			try {
				int patientId = Integer.parseUnsignedInt(stringPatientId);
				request.setAttribute("patientId", patientId);
				request.setAttribute("action", "doctor assigning");
				logger.info("List is formed to select a doctor");
			} catch (NumberFormatException e) {
				logger.error("Wrong patient Id format is sent before select a doctor");
				request.setAttribute("message", "invalidData");
				return "WEB-INF/errors/errorMessage.jsp";
			}
		}
		
		String locale = request.getSession().getAttribute("lang").toString();
		Page<DoctorDto> page = doctorService.findAllPaginated(pageNumber, sort, locale);
		logger.info(page);
		
		request.setAttribute("page", page);
		return "WEB-INF/views/doctorList.jsp";
	}

}
