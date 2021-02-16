package ua.hospital.servletapp.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents Patient entity used in app's Model part.
 * 
 * @author Alexander-PC
 *
 */

public class Patient {
	
	private int id;
	
	private Person person;
	private LocalDateTime arriveTime;
		
	private String diagnosisEn;
	private String diagnosisUk;
	
	private LocalDateTime dischargeDateTime;
	private Doctor doctor;
	
	private List<Prescription> prescriptions = new ArrayList<>();
	private Set<Meal> meals = new HashSet<>();
		
	public Patient() {
	}
		
	public Patient(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
		
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public LocalDateTime getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(LocalDateTime arriveTime) {
		this.arriveTime = arriveTime;
	}
		
	public String getDiagnosisEn() {
		return diagnosisEn;
	}
	public void setDiagnosisEn(String diagnosisEn) {
		this.diagnosisEn = diagnosisEn;
	}
	
	public String getDiagnosisUk() {
		return diagnosisUk;
	}
	public void setDiagnosisUk(String diagnosisUk) {
		this.diagnosisUk = diagnosisUk;
	}
	
	public LocalDateTime getDischargeDateTime() {
		return dischargeDateTime;
	}
	public void setDischargeDateTime(LocalDateTime dischargeTime) {
		this.dischargeDateTime = dischargeTime;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}
	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	public Set<Meal> getMeals() {
		return meals;
	}
	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("\nid: ")
				.append(id)
				.append(", person info: ")
				.append(person)
				.append("; arriving time: ")
				.append(arriveTime)
				.append("; diagnosis (en): ")
				.append(diagnosisEn)
				.append("; diagnosis (uk): ")
				.append(diagnosisUk)
				.append("; dischargeTime: ")
				.append(dischargeDateTime)
				.append(";\n doctor info: ")
				.append(doctor)
				.append("\n prescriptions: ")
				.append(prescriptions)
				.append(";\n meals: ")
				.append(meals)
				.toString();
	}
		
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Patient patient;
		
		public Builder() {
			patient = new Patient();
		}
		
		public Builder withId(int id) {
			patient.id = id;
			return this;
		}
		
		public Builder withPerson(Person person) {
			patient.person = person;
			return this;
		}
		
		public Builder withArriveDate(LocalDateTime arriveTime) {
			patient.arriveTime = arriveTime;
			return this;
		}
		
		public Builder withDiagnosisEn(String diagnosisEn) {
			patient.diagnosisEn = diagnosisEn;
			return this;
		}
		
		public Builder withDiagnosisUk(String diagnosisUk) {
			patient.diagnosisUk = diagnosisUk;
			return this;
		}
		
		public Builder withDischargeDateTime(LocalDateTime dischargeDateTime) {
			patient.dischargeDateTime = dischargeDateTime;
			return this;
		}
		
		public Builder withDoctor(Doctor doctor) {
			patient.doctor = doctor;
			return this;
		}
		
		public Builder withPrescriptions(List<Prescription> prescriptions) {
			patient.prescriptions = prescriptions;
			return this;
		}
		
		public Builder withMeals(Set<Meal> meals) {
			patient.meals = meals;
			return this;
		}
		
		public Patient build() {
			return patient;
		}
		
	}
	
}
