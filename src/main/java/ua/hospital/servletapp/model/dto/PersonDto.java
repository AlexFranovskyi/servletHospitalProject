package ua.hospital.servletapp.model.dto;

import java.time.LocalDate;

public class PersonDto {
	private int id;
	
	private String firstNameEn;
	private String firstNameUk;
	
	private String lastNameEn;
	private String lastNameUk;
	private LocalDate birthDate;
	
	public int getId() {
		return id;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public String getFirstNameUk() {
		return firstNameUk;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public String getLastNameUk() {
		return lastNameUk;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}

	public void setFirstNameUk(String firstNameUk) {
		this.firstNameUk = firstNameUk;
	}

	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}

	public void setLastNameUk(String lastNameUk) {
		this.lastNameUk = lastNameUk;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", nameEn: ")
				.append(firstNameEn)
				.append(' ')
				.append(lastNameEn)
				.append(", nameUk: ")
				.append(firstNameUk)
				.append(' ')
				.append(lastNameUk)
				.append("; date of birth: ")
				.append(birthDate)
				.toString();		
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		private PersonDto personDto;
		
		private Builder() {
			personDto = new PersonDto();
		}
		
		public Builder withId(int id) {
			personDto.id = id;
			return this;
		}
		
		public Builder withFirstNameEn(String firstNameEn) {
			personDto.firstNameEn = firstNameEn;
			return this;
		}
		
		public Builder withFirstNameUk(String firstNameUk) {
			personDto.firstNameUk = firstNameUk;
			return this;
		}
		
		public Builder withLastNameEn(String lastNameEn) {
			personDto.lastNameEn = lastNameEn;
			return this;
		}
		
		public Builder withLastNameUk(String lastNameUk) {
			personDto.lastNameUk = lastNameUk;
			return this;
		}
		
		public Builder withBirthDate(LocalDate birthDate) {
			personDto.birthDate = birthDate;
			return this;
		}
		
		public PersonDto build() {
			return personDto;
		}
	}

}
