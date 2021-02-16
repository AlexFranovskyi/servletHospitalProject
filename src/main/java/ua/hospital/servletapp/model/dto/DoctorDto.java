package ua.hospital.servletapp.model.dto;

public class DoctorDto {
	
	private int id;
	private UserDto userDto;
	private CategoryDto categoryDto;
	private int patientAmount;
	
	public DoctorDto() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
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
				.append(userDto)
				.append(";\n category - ")
				.append(categoryDto)
				.append("; amount of patients: ")
				.append(patientAmount)
				.append(';')
				.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		DoctorDto doctorDto;
		
		public Builder() {
			doctorDto = new DoctorDto();
		}
		
		public Builder withId(int id) {
			doctorDto.id = id;
			return this;
		}
		
		public Builder withUserDto(UserDto userDto) {
			doctorDto.setUserDto(userDto);
			return this;
		}
		
		public Builder withCategoryDto(CategoryDto categoryDto) {
			doctorDto.categoryDto = categoryDto;
			return this;
		}
		
		public Builder withPatientAmount(int patientAmount) {
			doctorDto.patientAmount = patientAmount;
			return this;
		}
		
		public DoctorDto build() {
			return doctorDto;
		}
	}
	
}
