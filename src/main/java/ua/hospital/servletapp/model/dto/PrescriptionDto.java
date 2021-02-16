package ua.hospital.servletapp.model.dto;

import java.time.LocalDateTime;

import ua.hospital.servletapp.model.entity.Prescription.PrescriptionType;

public class PrescriptionDto {
	
	private int id;
	private String descriptionEn;
	private String descriptionUk;
	
	private PrescriptionType type;
	private LocalDateTime completionTime;
	private int patientId;
	
	public PrescriptionDto() {
	}

	public PrescriptionDto(int id) {
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

	public int getPatientId() {
		return patientId;
	}

	public void setPatientDto(int patientId) {
		this.patientId = patientId;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", patient id: ")
				.append(patientId)
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
		private PrescriptionDto prescriptionDto;
		
		public Builder() {
			prescriptionDto = new PrescriptionDto();
		}
		
		public Builder withId(int id) {
			prescriptionDto.id = id;
			return this;
		}
		
		public Builder withDescriptionEn(String descriptionEn) {
			prescriptionDto.descriptionEn = descriptionEn;
			return this;
		}
		
		public Builder withDescriptionUk(String descriptionUk) {
			prescriptionDto.descriptionUk = descriptionUk;
			return this;
		}
		
		public Builder withPrescriptionType(PrescriptionType type) {
			prescriptionDto.type = type;
			return this;
		}
		
		public Builder withCompletionTime(LocalDateTime completionTime) {
			prescriptionDto.completionTime = completionTime;
			return this;
		}
		
		public Builder withPatientId(int patientId) {
			prescriptionDto.patientId = patientId;
			return this;
		}
		
		public PrescriptionDto build() {
			return prescriptionDto;
		}
		
	}

}
