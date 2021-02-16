package ua.hospital.servletapp.model.dao;

import ua.hospital.servletapp.model.entity.Prescription;

public interface PrescriptionDao extends GenericDao<Prescription> {
	boolean completePrescription(int prescriptionId);
}
