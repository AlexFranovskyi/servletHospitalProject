package ua.hospital.servletapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.hospital.servletapp.controller.command.CategoryAssignCommand;
import ua.hospital.servletapp.controller.command.AuthenticationCommand;
import ua.hospital.servletapp.controller.command.CategoryCreateCommand;
import ua.hospital.servletapp.controller.command.CategoryDeleteCommand;
import ua.hospital.servletapp.controller.command.CategoryListCommand;
import ua.hospital.servletapp.controller.command.CategoryUpdateCommand;
import ua.hospital.servletapp.controller.command.Command;
import ua.hospital.servletapp.controller.command.DoctorAssignCommand;
import ua.hospital.servletapp.controller.command.DoctorListCommand;
import ua.hospital.servletapp.controller.command.DoctorPatientsListCommand;
import ua.hospital.servletapp.controller.command.LoginCommand;
import ua.hospital.servletapp.controller.command.LogoutCommand;
import ua.hospital.servletapp.controller.command.MealCreateCommand;
import ua.hospital.servletapp.controller.command.MealDeleteCommand;
import ua.hospital.servletapp.controller.command.MealListCommand;
import ua.hospital.servletapp.controller.command.MealUpdateCommand;
import ua.hospital.servletapp.controller.command.PatientDiagnosisCommand;
import ua.hospital.servletapp.controller.command.PatientDischargeCommand;
import ua.hospital.servletapp.controller.command.PatientMealAssignCommand;
import ua.hospital.servletapp.controller.command.PatientMealRemoveCommand;
import ua.hospital.servletapp.controller.command.PatientListCommand;
import ua.hospital.servletapp.controller.command.PatientGetCommand;
import ua.hospital.servletapp.controller.command.PatientRegisterCommand;
import ua.hospital.servletapp.controller.command.PatientRegistrationFormCommand;
import ua.hospital.servletapp.controller.command.PrescriptionCompleteCommand;
import ua.hospital.servletapp.controller.command.PrescriptionCreateCommand;
import ua.hospital.servletapp.controller.command.PrescriptionDeleteCommand;
import ua.hospital.servletapp.controller.command.ProfileCommand;
import ua.hospital.servletapp.controller.command.ProfileOwnCommand;
import ua.hospital.servletapp.controller.command.UserRegisterCommand;
import ua.hospital.servletapp.controller.command.UserRegistrationFormCommand;

/**
 * @author Alexander-PC
 * 
 * This class is the main servlet in the package dispatchering all requests/responses.
 *
 */

public class Servlet extends HttpServlet {
	
	private final static Logger logger = LogManager.getLogger(Servlet.class);
	
	private static final long serialVersionUID = 1L;
	private Map<String, Command> commands = new HashMap<>();
	
	@Override
	public void init(ServletConfig servletConfig) {
		servletConfig.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
		
		commands.put("login",
				new LoginCommand());
		commands.put("authentication",
				new AuthenticationCommand());
		commands.put("logout",
				new LogoutCommand());
		
		commands.put("register_user",
				new UserRegisterCommand());
		commands.put("user_registration_form",
				new UserRegistrationFormCommand());
		commands.put("profile",
				new ProfileCommand());
		commands.put("profile_own",
				new ProfileOwnCommand());
		
		commands.put("create_category",
				new CategoryCreateCommand());
		commands.put("assign_category",
				new CategoryAssignCommand());
		commands.put("category_update",
				new CategoryUpdateCommand());
		commands.put("category_delete",
				new CategoryDeleteCommand());
		commands.put("category_list",
				new CategoryListCommand());
		
		commands.put("doctor_list",
				new DoctorListCommand());
		
		commands.put("register_patient",
				new PatientRegisterCommand());
		commands.put("patient_registration_form",
				new PatientRegistrationFormCommand());
		commands.put("assign_doctor",
				new DoctorAssignCommand());
		commands.put("define_diagnosis",
				new PatientDiagnosisCommand());
		commands.put("discharge_patient", 
				new PatientDischargeCommand());
		commands.put("assign_patient_meal",
				new PatientMealAssignCommand());
		commands.put("remove_patient_meal",
				new PatientMealRemoveCommand());
		commands.put("patient_get",
				new PatientGetCommand());
		commands.put("patients_list", 
				new PatientListCommand());
		commands.put("doctor_patients_list",
				new DoctorPatientsListCommand());
		
		commands.put("create_prescription",
				new PrescriptionCreateCommand());
		commands.put("complete_prescription",
				new PrescriptionCompleteCommand());
		commands.put("delete_prescription",
				new PrescriptionDeleteCommand());
		
		commands.put("create_meal",
				new MealCreateCommand());
		commands.put("update_meal",
				new MealUpdateCommand());
		commands.put("delete_meal",
				new MealDeleteCommand());
		commands.put("meal_list",
				new MealListCommand());		
		
		logger.info("Servlet is initialized");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);

	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getRequestURI();
        
        logger.info("processing http request with URI: " + path);
        
        path = path.replaceAll(".*/servletHospitalProject/" , "");
        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp");
        
        String page = command.execute(req);
        
        if(page.contains("redirect:")){
            resp.sendRedirect(page.replace("redirect:", "/servletHospitalProject"));
            return;
        }
        req.getRequestDispatcher(page).forward(req, resp);
        
    }
}
