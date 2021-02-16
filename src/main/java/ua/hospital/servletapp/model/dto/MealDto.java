package ua.hospital.servletapp.model.dto;

public class MealDto {
	
	private int id;
	private String nameEn;
	private String nameUk;
	
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
		private MealDto mealDto;
		
		public Builder() {
			mealDto = new MealDto();
		}
		
		public Builder withId(int id) {
			mealDto.id = id;
			return this;
		}
		
		public Builder withNameEn(String nameEn) {
			mealDto.nameEn = nameEn;
			return this;
		}
		
		public Builder withNameUk(String nameUk) {
			mealDto.nameUk = nameUk;
			return this;
		}
		
		public MealDto build() {
			return mealDto;
		}
	}
}
