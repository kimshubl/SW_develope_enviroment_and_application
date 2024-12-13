package bank;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Scanner;

public class BankApp {
    private static final SecretKey aesKey = EncryptionUtil.generateFixedAESKey();
    private static final String FILE_PATH = "users.txt";

    public static void main(String[] args) {
        try {
            UserStorage storage = new UserStorage(FILE_PATH);
            Scanner scanner = new Scanner(System.in);

            // 사용자 등록
            System.out.println("사용자 정보를 입력하세요.");
            System.out.print("이름: ");
            String name = scanner.nextLine();
            System.out.print("주민등록번호: ");
            String rrn = scanner.nextLine();
            System.out.print("계좌번호: ");
            String account = scanner.nextLine();
            System.out.print("비밀번호: ");
            String password = scanner.nextLine();

            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(password, salt);

            String encryptedName = EncryptionUtil.encryptAES(name, aesKey);
            String encryptedRrn = EncryptionUtil.encryptAES(rrn, aesKey);
            String encryptedAccount = EncryptionUtil.encryptAES(account, aesKey);

            User newUser = new User(encryptedName, encryptedRrn, encryptedAccount, hashedPassword, salt);
            storage.saveUser(newUser);

            // 로그인 시도
            System.out.println("\n로그인을 시도합니다.");
            System.out.print("계좌번호: ");
            String inputAccount = scanner.nextLine();
            System.out.print("비밀번호: ");
            String inputPassword = scanner.nextLine();

            List<User> users = storage.loadUsers();
            String userName = loginAndGetUserName(users, inputAccount, inputPassword);
            
            if (userName != null) {
                System.out.println("로그인 성공! 환영합니다, " + userName);
            } else {
                System.out.println("로그인 실패: 계좌번호 또는 비밀번호가 잘못되었습니다.");
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("프로그램 실행 중 오류 발생: " + e.getMessage());
        }
    }
    

    public static String loginAndGetUserName(List<User> users, String inputAccount, String inputPassword) throws Exception {
        for (User user : users) {
            String decryptedAccount = EncryptionUtil.decryptAES(user.getAccountNumber(), aesKey);
            if (decryptedAccount.equals(inputAccount)) {
                if (PasswordUtil.verifyPassword(inputPassword, user.getHashedPassword(), user.getSalt())) {
                    String decryptedName = EncryptionUtil.decryptAES(user.getName(), aesKey);
                    return decryptedName; // 로그인 성공 시 사용자 이름 반환
                }
            }
        }
        return null; // 로그인 실패 시 null 반환
    }
    
}

