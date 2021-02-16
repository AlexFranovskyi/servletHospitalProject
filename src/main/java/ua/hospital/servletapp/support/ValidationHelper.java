package ua.hospital.servletapp.support;

import java.util.regex.Pattern;

/**
 * This class contains method to validate input String parameters against regex patterns.
 * 
 * @author Alexander-PC
 *
 */
public class ValidationHelper {
	
	public static final Pattern patternLatinName = Pattern.compile(Constants.LATIN_NAME_REGEX);
	public static final Pattern patternCyrrilicName = Pattern.compile(Constants.CYRILLIC_NAME_REGEX);
	public static final Pattern patternPassword = Pattern.compile(Constants.PASSWORD_REGEX);
	public static final Pattern patternLogin = Pattern.compile(Constants.LOGIN_REGEX);
	public static final Pattern patternPhraseEn = Pattern.compile(Constants.PHRASE_EN_REGEX);
	public static final Pattern patternPhraseUk = Pattern.compile(Constants.PHRASE_UK_REGEX);
		
	public static boolean isLatinName(String input) {
		return patternLatinName.matcher(input).matches();		
	}
	
	public static boolean isCyrillicName(String input) {
		return patternCyrrilicName.matcher(input).matches();		
	}
	
	public static boolean isPassword(String input) {
		return patternPassword.matcher(input).matches();
	}
	
	public static boolean isLogin(String input) {
		return patternLogin.matcher(input).matches();
	}
	
	public static boolean isPhraseEn(String input) {
		return patternPhraseEn.matcher(input).matches();
	}
	
	public static boolean isPhraseUk(String input) {
		return patternPhraseUk.matcher(input).matches();
	}

}
