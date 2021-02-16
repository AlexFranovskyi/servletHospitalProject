package ua.hospital.servletapp.model.dao;

import ua.hospital.servletapp.model.dao.impl.JdbcDaoFactory;

public abstract class DaoFactory {
	private static DaoFactory daoFactory;
	
	public abstract UserDao createUserDao();
	public abstract PrescriptionDao createPrescriptionDao();
	public abstract PatientDao createPatientDao();
	public abstract MealDao createMealDao();
	public abstract DoctorDao createDoctorDao();
	public abstract CategoryDao createCategoryDao();
	
	public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JdbcDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }

}
