package test3;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
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
            System.out.print("password : " + password);
            System.out.print("salt : " + salt);
            
            String saltedPassword = password + salt;
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());

            // 디버깅
            System.out.println("hashedBytes length: " + hashedBytes.length);
            System.out.println("hashedBytes (raw): " + Arrays.toString(hashedBytes));

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