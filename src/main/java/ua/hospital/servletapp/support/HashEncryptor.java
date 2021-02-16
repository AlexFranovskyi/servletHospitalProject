package ua.hospital.servletapp.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashEncryptor {
	
	public static final char[] HEX_CHAR = "0123456789ABCDEF".toCharArray();
	private static final byte[] SALT = "seven11".getBytes();
	
	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		int b;
		for (int i = 0; i < bytes.length; i++) {
			b = bytes[i] & 0xFF;
			hexChars[i * 2] = HEX_CHAR[b >>> 4];
			hexChars[i * 2 + 1] = HEX_CHAR[b & 0x0F];	
		}
		return new String(hexChars);
	}
	
	public static String encryptString(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(Constants.ENCRYPTION_ALGORITHM);
		md.update(SALT);
		md.update(input.getBytes());
		return bytesToHex(md.digest());
	}
	
}
