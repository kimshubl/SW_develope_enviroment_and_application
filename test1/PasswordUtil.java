package test1;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 알고리즘 오류: " + e.getMessage());
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHashedPassword, String salt) {
        String hashedInput = hashPassword(inputPassword, salt);
        return hashedInput.equals(storedHashedPassword);
    }
}
