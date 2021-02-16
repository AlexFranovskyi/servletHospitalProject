package ua.hospital.servletapp.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatientDto {
	
	private int id;
	
	private PersonDto personDto;
	private LocalDateTime arriveTime;
	
	private String diagnosisEn;
	private String diagnosisUk;
	
	private LocalDateTime dischargeDateTime;
	private DoctorDto doctorDto;
	
	private List<PrescriptionDto> prescriptionDtoList = new ArrayList<>();
	private Set<MealDto> mealDtoSet = new HashSet<>();
	
	public PatientDto() {
	}

	public PatientDto(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonDto getPersonDto() {
		return personDto;
	}

	public void setPersonDto(PersonDto personDto) {
		this.personDto = personDto;
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

	public void setDischargeDateTime(LocalDateTime dischargeDateTime) {
		this.dischargeDateTime = dischargeDateTime;
	}

	public DoctorDto getDoctorDto() {
		return doctorDto;
	}

	public void setDoctorDto(DoctorDto doctorDto) {
		this.doctorDto = doctorDto;
	}

	public List<PrescriptionDto> getPrescriptionDtoList() {
		return prescriptionDtoList;
	}

	public void setPrescriptionDtoList(List<PrescriptionDto> prescriptionDtoList) {
		this.prescriptionDtoList = prescriptionDtoList;
	}

	public Set<MealDto> getMealDtoSet() {
		return mealDtoSet;
	}

	public void setMealDtoSet(Set<MealDto> mealDtoSet) {
		this.mealDtoSet = mealDtoSet;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("\nid: ")
				.append(id)
				.append(", person info: ")
				.append(personDto)
				.append("; arriving time: ")
				.append(arriveTime)
				.append("; diagnosis (en): ")
				.append(diagnosisEn)
				.append("; diagnosis (uk): ")
				.append(diagnosisUk)
				.append("; dischargeTime: ")
				.append(dischargeDateTime)
				.append(";\n doctor info: ")
				.append(doctorDto)
				.append("\n prescriptions: ")
				.append(prescriptionDtoList)
				.append(";\n meals: ")
				.append(mealDtoSet)
				.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private PatientDto patientDto;
		
		public Builder() {
			patientDto = new PatientDto();
		}
		
		public Builder withId(int id) {
			patientDto.id = id;
			return this;
		}
		
		public Builder withPersonDto(PersonDto personDto) {
			patientDto.personDto = personDto;
			return this;
		}
		
		public Builder withArriveDate(LocalDateTime arriveTime) {
			patientDto.arriveTime = arriveTime;
			return this;
		}
		
		public Builder withDiagnosisEn(String diagnosisEn) {
			patientDto.diagnosisEn = diagnosisEn;
			return this;
		}
		
		public Builder withDiagnosisUk(String diagnosisUk) {
			patientDto.diagnosisUk = diagnosisUk;
			return this;
		}
		
		public Builder withDischargeDateTime(LocalDateTime dischargeDateTime) {
			patientDto.dischargeDateTime = dischargeDateTime;
			return this;
		}
		
		public Builder withDoctorDto(DoctorDto doctorDto) {
			patientDto.doctorDto = doctorDto;
			return this;
		}
		
		public Builder withPrescriptionDtoList(List<PrescriptionDto> prescriptions) {
			patientDto.prescriptionDtoList = prescriptions;
			return this;
		}
		
		public Builder withMealDtoSet(Set<MealDto> meals) {
			patientDto.mealDtoSet = meals;
			return this;
		}
		
		public PatientDto build() {
			return patientDto;
		}
		
	}

}
