package ua.hospital.servletapp.model.service;

import ua.hospital.servletapp.model.dao.DaoFactory;
import ua.hospital.servletapp.model.dao.PrescriptionDao;
import ua.hospital.servletapp.model.entity.Prescription;

public class PrescriptionService {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	
	public boolean createPrescription(Prescription prescription) {
		PrescriptionDao prescriptionDao = daoFactory.createPrescriptionDao();
		prescriptionDao.create(prescription);
		prescriptionDao.close();
		return prescription.getId() != 0;
	}
	
	public boolean completePrescription(int prescriptionId) {
		PrescriptionDao prescriptionDao = daoFactory.createPrescriptionDao();
		boolean result = prescriptionDao.completePrescription(prescriptionId);
		prescriptionDao.close();
		return result;
	}
	
	public boolean deletePrescription(int prescriptionId) {
		PrescriptionDao prescriptionDao = daoFactory.createPrescriptionDao();
		boolean result = prescriptionDao.delete(prescriptionId);
		prescriptionDao.close();
		return result;
	}

}
