package ua.hospital.servletapp.model.entity;

/**
 * @author Alexander-PC
 * 
 * This class represents User entity used in webapp's Model part. Includes Role enum containing all possible user's roles.
 *
 */

public class User {
	
	public enum Role {
		GUEST, NURSE, DOCTOR, ADMIN			
	}
	
	private int id;
	private Role role;
	
	private Person person;
	
	private String login;
	private String password;
	
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
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		private User user;
		
		private Builder() {
			user = new User();
		}
		
		public Builder withId(int id) {
			user.id = id;
			return this;
		}
		
		public Builder withRole(Role role) {
			user.role = role;
			return this;
		}
		
		public Builder withPerson(Person person) {
			user.person = person;
			return this;
		}
		
		public Builder withLogin(String login) {
			user.login = login;
			return this;
		}
		
		public Builder withPassword(String password) {
			user.password = password;
			return this;
		}
		
		public User build() {
			return user;
		}
		
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", person info: ")
				.append(person)
				.append(", login: ")
				.append(login)
				.append(", password: ")
				.append(password)
				.toString();
	}
	
}
