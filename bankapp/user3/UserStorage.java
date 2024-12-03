package user3;

import java.io.*;
import java.util.*;

public class UserStorage {
	private static final String FILE_PATH = "users.txt"; // 사용자 정보가 저장될 TXT 파일 경로

    // 사용자 정보를 파일에 추가하는 메서드
    public static void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toTextFormat());
            writer.newLine(); // 사용자 정보를 한 줄씩 구분
            System.out.println("User added: " + user.userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 사용자 정보를 읽어오는 메서드
    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromTextFormat(line)); // 텍스트 형식으로 저장된 사용자 정보를 User 객체로 변환
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void main(String[] args) {
        // 사용자 추가 예시
        User newUser = new User("101", "John Doe", "password123", "123456789", 5000.0);
        addUser(newUser);

        // 사용자 읽기 예시
        List<User> users = readUsers();
        for (User user : users) {
            System.out.println(user); // 사용자 정보 출력
        }
    }

}
