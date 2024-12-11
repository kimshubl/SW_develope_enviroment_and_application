package test1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
	private String filePath;

    public UserStorage(String filePath) {
        this.filePath = filePath;
    }

    // 사용자 정보를 파일에 저장
    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.toCSV());
            writer.newLine();
            System.out.println("사용자 정보가 성공적으로 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 파일을 초기화하는 메서드
    public void clearFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 파일 내용을 비우기 위해 아무것도 쓰지 않고 닫기
            System.out.println("파일이 초기화되었습니다.");
        } catch (IOException e) {
            System.out.println("파일 초기화 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 파일에서 사용자 정보를 불러오기
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCSV(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return users;
    }
}
	