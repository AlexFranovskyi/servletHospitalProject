package ua.hospital.servletapp.model.entity;

import java.time.LocalDate;

public class Person {
	
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
		private Person person;
		
		private Builder() {
			person = new Person();
		}
		
		public Builder withId(int id) {
			person.id = id;
			return this;
		}
		
		public Builder withFirstNameEn(String firstNameEn) {
			person.firstNameEn = firstNameEn;
			return this;
		}
		
		public Builder withFirstNameUk(String firstNameUk) {
			person.firstNameUk = firstNameUk;
			return this;
		}
		
		public Builder withLastNameEn(String lastNameEn) {
			person.lastNameEn = lastNameEn;
			return this;
		}
		
		public Builder withLastNameUk(String lastNameUk) {
			person.lastNameUk = lastNameUk;
			return this;
		}
		
		public Builder withBirthDate(LocalDate birthDate) {
			person.birthDate = birthDate;
			return this;
		}
		
		public Person build() {
			return person;
		}
	}
	
}
