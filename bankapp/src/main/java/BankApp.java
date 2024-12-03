import javax.crypto.SecretKey;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankApp {
    private static SecretKey aesKey;
    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) throws Exception {
        // AES 키 생성
        aesKey = EncryptionUtil.generateAESKey();

        Scanner scanner = new Scanner(System.in);

        // 사용자 등록
        System.out.println("사용자 정보를 입력하세요.");
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("주민등록번호: ");
        String residentRegistrationNumber = scanner.nextLine();
        System.out.print("계좌번호: ");
        String accountNumber = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        // 사용자 등록 및 저장
        registerUser(name, residentRegistrationNumber, accountNumber, password);

        // 저장된 사용자 정보 불러오기
        List<User> users = loadUsers();

        // 로그인 시도
        System.out.println("\n로그인을 시도합니다.");
        System.out.print("비밀번호를 입력하세요: ");
        String inputPassword = scanner.nextLine();
        login(users, inputPassword);

        scanner.close();
    }

    // 사용자 등록 및 저장 메서드
    public static void registerUser(String name, String rrn, String account, String password) throws Exception {
        String encryptedName = EncryptionUtil.encryptAES(name, aesKey);
        String encryptedRrn = EncryptionUtil.encryptAES(rrn, aesKey);
        String encryptedAccount = EncryptionUtil.encryptAES(account, aesKey);
        String hashedPassword = PasswordUtil.hashPassword(password);

        User newUser = new User(encryptedName, encryptedRrn, encryptedAccount, hashedPassword);
        saveUser(newUser);
        System.out.println("사용자가 성공적으로 등록되었습니다.");
    }

    // 사용자 정보 파일에 저장하는 메서드
    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(user.getName() + "," + user.getResidentRegistrationNumber() + "," +
                    user.getAccountNumber() + "," + user.getHashedPassword());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하는 중 오류가 발생했습니다.");
        }
    }

    // 파일에서 사용자 정보를 불러오는 메서드
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    users.add(new User(data[0], data[1], data[2], data[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 중 오류가 발생했습니다.");
        }
        return users;
    }

    // 로그인 메서드
    public static void login(List<User> users, String inputPassword) throws Exception {
        for (User user : users) {
            if (PasswordUtil.verifyPassword(inputPassword, user.getHashedPassword())) {
                String decryptedName = EncryptionUtil.decryptAES(user.getName(), aesKey);
                String decryptedRrn = EncryptionUtil.decryptAES(user.getResidentRegistrationNumber(), aesKey);
                String decryptedAccount = EncryptionUtil.decryptAES(user.getAccountNumber(), aesKey);

                System.out.println("로그인 성공! 환영합니다, " + decryptedName);
                System.out.println("주민등록번호: " + decryptedRrn);
                System.out.println("계좌번호: " + decryptedAccount);
                return;
            }
        }
        System.out.println("로그인 실패: 비밀번호가 일치하지 않습니다.");
    }
}
