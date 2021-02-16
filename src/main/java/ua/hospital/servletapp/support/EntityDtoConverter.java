package ua.hospital.servletapp.support;

import java.util.Optional;
import java.util.stream.Collectors;

import ua.hospital.servletapp.model.dto.CategoryDto;
import ua.hospital.servletapp.model.dto.DoctorDto;
import ua.hospital.servletapp.model.dto.MealDto;
import ua.hospital.servletapp.model.dto.PatientDto;
import ua.hospital.servletapp.model.dto.PersonDto;
import ua.hospital.servletapp.model.dto.PrescriptionDto;
import ua.hospital.servletapp.model.dto.UserDto;
import ua.hospital.servletapp.model.entity.Category;
import ua.hospital.servletapp.model.entity.Doctor;
import ua.hospital.servletapp.model.entity.Meal;
import ua.hospital.servletapp.model.entity.Patient;
import ua.hospital.servletapp.model.entity.Person;
import ua.hospital.servletapp.model.entity.Prescription;
import ua.hospital.servletapp.model.entity.User;

public class EntityDtoConverter {
	
	public static PersonDto PersonToDto(Person person) {
		return PersonDto.builder()
				.withId(person.getId())
				.withFirstNameEn(person.getFirstNameEn())
				.withFirstNameUk(person.getFirstNameUk())
				.withLastNameEn(person.getLastNameEn())
				.withLastNameUk(person.getLastNameUk())
				.withBirthDate(person.getBirthDate())
				.build();
	}
	
	public static UserDto UserToDto(User user) {
		return UserDto.builder()
				.withId(user.getId())
				.withLogin(user.getLogin())
				.withPersonDto(Optional.ofNullable(user.getPerson())
						.map(EntityDtoConverter::PersonToDto).orElse(null))
				.withRole(user.getRole())
				.build();
	}
	
	public static DoctorDto DoctorToDto(Doctor doctor) {
		return DoctorDto.builder()
				.withId(doctor.getId())
				.withUserDto(Optional.ofNullable(doctor.getUser())
						.map(EntityDtoConverter::UserToDto).orElse(null))
				.withCategoryDto(Optional.ofNullable(doctor.getCategory())
						.map(EntityDtoConverter::CategoryToDto).orElse(null))
				.withPatientAmount(doctor.getPatientAmount())
				.build();
	}
	
	public static CategoryDto CategoryToDto(Category category) {
		return CategoryDto.builder()
				.withId(category.getId())
				.withNameEn(category.getNameEn())
				.withNameUk(category.getNameUk())
				.build();
	}
	
	public static MealDto MealToDto(Meal meal) {
		return MealDto.builder()
				.withId(meal.getId())
				.withNameEn(meal.getNameEn())
				.withNameUk(meal.getNameUk())
				.build();
	}
	
	public static PrescriptionDto PrescriptionToDto(Prescription prescription) {
		return PrescriptionDto.builder()
				.withId(prescription.getId())
				.withDescriptionEn(prescription.getDescriptionEn())
				.withDescriptionUk(prescription.getDescriptionUk())
				.withPrescriptionType(prescription.getType())
				.withCompletionTime(prescription.getCompletionTime())
				.withPatientId(Optional.ofNullable(prescription.getPatient())
						.map(p -> p.getId()).orElse(null))
				.build();
	}
	
	public static PatientDto PatientToDto(Patient patient) {
		return PatientDto.builder()
				.withId(patient.getId())
				.withPersonDto(Optional.ofNullable(patient.getPerson())
						.map(EntityDtoConverter::PersonToDto).orElse(null))
				
				.withArriveDate(patient.getArriveTime())
				.withDiagnosisEn(patient.getDiagnosisEn())
				.withDiagnosisUk(patient.getDiagnosisUk())
				.withDischargeDateTime(patient.getDischargeDateTime())
				
				.withDoctorDto(Optional.ofNullable(patient.getDoctor())					
						.map(EntityDtoConverter::DoctorToDto).orElse(null))
				
				.withPrescriptionDtoList(patient.getPrescriptions().stream()					
						.map(EntityDtoConverter::PrescriptionToDto).collect(Collectors.toList()))
				
				.withMealDtoSet(patient.getMeals().stream()
						.map(EntityDtoConverter::MealToDto).collect(Collectors.toSet()))
				.build();
	}

}
