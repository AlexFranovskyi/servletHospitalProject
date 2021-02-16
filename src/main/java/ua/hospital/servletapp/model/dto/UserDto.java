package ua.hospital.servletapp.model.dto;

import ua.hospital.servletapp.model.entity.User.Role;

public class UserDto {
	
	private int id;
	private Role role;
	
	private PersonDto personDto;
	
	private String login;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public PersonDto getPersonDto() {
		return personDto;
	}
	public void setPersonDto(PersonDto personDto) {
		this.personDto = personDto;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		private UserDto userDto;
		
		private Builder() {
			userDto = new UserDto();
		}
		
		public Builder withId(int id) {
			userDto.id = id;
			return this;
		}
		
		public Builder withRole(Role role) {
			userDto.role = role;
			return this;
		}
		
		public Builder withPersonDto(PersonDto personDto) {
			userDto.personDto = personDto;
			return this;
		}
		
		public Builder withLogin(String login) {
			userDto.login = login;
			return this;
		}
				
		public UserDto build() {
			return userDto;
		}
		
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", person info: ")
				.append(personDto)
				.append(", login: ")
				.append(login)
				.toString();
	}

}
