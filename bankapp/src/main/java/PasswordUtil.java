import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {
    // 비밀번호 해싱 메서드
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.");
        }
    }

    // 해시된 비밀번호 검증 메서드
    public static boolean verifyPassword(String inputPassword, String storedHashedPassword) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(storedHashedPassword);
    }
}
