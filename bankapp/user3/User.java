package user3;

public class User {
	String userId;
    String name;
    String password;
    String phoneNumber;
    double balance;

    // 생성자
    public User(String userId, String name, String password, String phoneNumber, double balance) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    // 사용자 정보를 TXT 파일 형식으로 변환
    public String toTextFormat() {
        return userId + " " + name + " " + password + " " + phoneNumber + " " + balance;
    }

    // TXT 형식을 User 객체로 변환
    public static User fromTextFormat(String userData) {
        String[] parts = userData.split(" "); // 공백으로 구분
        return new User(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", name=" + name + ", balance=" + balance + "}";
    }

}
