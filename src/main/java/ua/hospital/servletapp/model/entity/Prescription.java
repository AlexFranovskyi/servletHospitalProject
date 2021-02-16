package ua.hospital.servletapp.model.entity;

import java.time.LocalDateTime;

/**
 * This class represents Prescription entity, used in app's Model part.
 * Includes enum {@link PrescriptionType}.
 * 
 * @author Alexander-PC
 *
 */

public class Prescription {
	
	private int id;
	private String descriptionEn;
	private String descriptionUk;
	
	private PrescriptionType type;
	private LocalDateTime completionTime;
	private Patient patient;

	public enum PrescriptionType {
		PROCEDURE, DRUGS, OPERATION
	}
	
	

	public Prescription() {
	}
	
	
	public Prescription(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public String getDescriptionUk() {
		return descriptionUk;
	}
	public void setDescriptionUk(String descriptionUk) {
		this.descriptionUk = descriptionUk;
	}

	public PrescriptionType getType() {
		return type;
	}
	public void setType(PrescriptionType type) {
		this.type = type;
	}

	public LocalDateTime getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(LocalDateTime completionTime) {
		this.completionTime = completionTime;
	}
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", patient id: ")
				.append(patient.getId())
				.append(", descriptionEn: ")
				.append(descriptionEn)
				.append(", descriptionUk: ")
				.append(descriptionUk)
				.append(", type: ")
				.append(type)
				.append(", completion time: ")
				.append(completionTime)
				.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private Prescription prescription;
		
		public Builder() {
			prescription = new Prescription();
		}
		
		public Builder withId(int id) {
			prescription.id = id;
			return this;
		}
		
		public Builder withDescriptionEn(String descriptionEn) {
			prescription.descriptionEn = descriptionEn;
			return this;
		}
		
		public Builder withDescriptionUk(String descriptionUk) {
			prescription.descriptionUk = descriptionUk;
			return this;
		}
		
		public Builder withPrescriptionType(PrescriptionType type) {
			prescription.type = type;
			return this;
		}
		
		public Builder withCompletionTime(LocalDateTime completionTime) {
			prescription.completionTime = completionTime;
			return this;
		}
		
		public Builder withPatient(Patient patient) {
			prescription.patient = patient;
			return this;
		}
		
		public Prescription build() {
			return prescription;
		}
		
	}
	
}
