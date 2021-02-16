package ua.hospital.servletapp.model.entity;

/**
 * This class represents DoctorProfile entity used in webapp model.
 * DoctorProfile object contains {@link User} and {@link Category} class objects.
 * 
 * The object of {@link User} class, passed into {@link Doctor} constructor 
 * or {@link Doctor#setUser(User)} setter method, should have property value of
 * {@link User#getRole()} method equal to {@link User.Role#DOCTOR} and should not be changed
 * due to data consistency needs.
 *  
 * @author Alexander-PC
 *
 */

public class Doctor {
	
	private int id;
	private User user;
	private Category category;
	private int patientAmount;
	
	public Doctor() {
		
	}
		
	public Doctor(User user) {
		this.user = user;
	}
	
	public Doctor(User user, Category category) {
		this.user = user;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public int getPatientAmount() {
		return patientAmount;
	}
	public void setPatientAmount(int patientAmount) {
		this.patientAmount = patientAmount;
	}

	@Override
	public String toString() {
		return new StringBuilder("\nDoctorProfile id: ")
				.append(id)
				.append("; User - ")
				.append(user)
				.append(";\n category - ")
				.append(category)
				.append("; amount of patients: ")
				.append(patientAmount)
				.append(';')
				.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		Doctor doctor;
		
		public Builder() {
			doctor = new Doctor();
		}
		
		public Builder withId(int id) {
			doctor.id = id;
			return this;
		}
		
		public Builder withUser(User user) {
			doctor.setUser(user);
			return this;
		}
		
		public Builder withCategory(Category category) {
			doctor.category = category;
			return this;
		}
		
		public Builder withPatientAmount(int patientAmount) {
			doctor.patientAmount = patientAmount;
			return this;
		}
		
		public Doctor build() {
			return doctor;
		}
	}
	
}
