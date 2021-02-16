package ua.hospital.servletapp.model.dto;

public class CategoryDto {
	
	private int id;
	private String nameEn;
	private String nameUk;
	
	public CategoryDto() {
		
	}

	public CategoryDto(String nameEn, String nameUk) {
		this.nameEn = nameEn;
		this.nameUk = nameUk;
	}

	public CategoryDto(int id, String nameEn, String nameUk) {
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
		private CategoryDto categoryDto;
		
		public Builder() {
			categoryDto = new CategoryDto();
		}
		
		public Builder withId(int id) {
			categoryDto.id = id;
			return this;
		}
		
		public Builder withNameEn(String nameEn) {
			categoryDto.nameEn = nameEn;
			return this;
		}
		
		public Builder withNameUk(String nameUk) {
			categoryDto.nameUk = nameUk;
			return this;
		}
		
		public CategoryDto build() {
			return categoryDto;
		}
	}

}
