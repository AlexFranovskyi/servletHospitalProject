package ua.hospital.servletapp.model.entity;

/**
 * This class represents Meal entity in webapp entity model.
 * 
 * @author Alexander-PC
 *
 */

public class Meal {
	
	private int id;
	private String nameEn;
	private String nameUk;
		
	public Meal() {
	}	
	
	public Meal(String nameEn, String nameUk) {
		this.nameEn = nameEn;
		this.nameUk = nameUk;
	}

	public Meal(int id, String nameEn, String nameUk) {
		this.id = id;
		this.nameEn = nameEn;
		this.nameUk = nameUk;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getNameUk() {
		return nameUk;
	}
	public void setNameUk(String nameUk) {
		this.nameUk = nameUk;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", nameEn: ")
				.append(nameEn)
				.append(", nameUk: ")
				.append(nameUk)
				.toString();
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private Meal meal;
		
		public Builder() {
			meal = new Meal();
		}
		
		public Builder withId(int id) {
			meal.id = id;
			return this;
		}
		
		public Builder withNameEn(String nameEn) {
			meal.nameEn = nameEn;
			return this;
		}
		
		public Builder withNameUk(String nameUk) {
			meal.nameUk = nameUk;
			return this;
		}
		
		public Meal build() {
			return meal;
		}
	}
}
