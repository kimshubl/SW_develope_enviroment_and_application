package test3;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Base64;

public class EncryptionUtil {

    // 고정된 AES 키 생성 (16바이트)
    public static SecretKey generateFixedAESKey() {
        byte[] keyBytes = "1234567890123456".getBytes(); // 고정된 16바이트 키
        return new SecretKeySpec(keyBytes, "AES");
    }

    // AES 암호화 (CBC 모드 + PKCS5Padding 패딩)
    public static String encryptAES(String data, SecretKey key) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("암호화할 데이터가 null이거나 비어 있습니다.");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // AES 복호화 (CBC 모드 + PKCS5Padding 패딩)
    public static String decryptAES(String encryptedData, SecretKey key) throws Exception {
        if (encryptedData == null || encryptedData.isEmpty()) {
            throw new IllegalArgumentException("복호화할 데이터가 null이거나 비어 있습니다.");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, "UTF-8");
    }
}