package ua.hospital.servletapp.model.entity;

/**
 * @author Alexander-PC
 * 
 * This class represents Category entity used in app's model part.
 *
 */

public class Category {
	
	private int id;
	private String nameEn;
	private String nameUk;
	
	public Category() {
		
	}
	
	public Category(String nameEn, String nameUk) {
		this.nameEn = nameEn;
		this.nameUk = nameUk;
	}
	
	public Category(int id, String nameEn, String nameUk) {
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
				.append(", nameEn:")
				.append(nameEn)
				.append(", nameUk: ")
				.append(nameUk)
				.toString();
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private Category category;
		
		public Builder() {
			category = new Category();
		}
		
		public Builder withId(int id) {
			category.id = id;
			return this;
		}
		
		public Builder withNameEn(String nameEn) {
			category.nameEn = nameEn;
			return this;
		}
		
		public Builder withNameUk(String nameUk) {
			category.nameUk = nameUk;
			return this;
		}
		
		public Category build() {
			return category;
		}
	}
	
	
}
