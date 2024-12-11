package bankapp2;

import java.io.*;

public class UserAccount {
	    private String name;       // 사용자 이름
	    private double balance;    // 계좌 잔액
	    private static final String FILE_PATH = "user_data.txt"; // 데이터 저장 파일 경로

	    // 생성자
	    public UserAccount(String name, double balance) {
	        this.name = name;
	        this.balance = balance;
	    }

	    // Getter
	    public String getName() {
	        return name;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    // 입금 메서드
	    public void deposit(double amount) {
	        if (amount > 0) {
	            balance += amount;
	            System.out.println(amount + "원이 입금되었습니다.");
	        } else {
	            System.out.println("입금 금액이 유효하지 않습니다.");
	        }
	    }

	    // 출금 메서드
	    public void withdraw(double amount) {
	        if (amount > 0 && amount <= balance) {
	            balance -= amount;
	            System.out.println(amount + "원이 출금되었습니다.");
	        } else {
	            System.out.println("잔액이 부족하거나 금액이 유효하지 않습니다.");
	        }
	    }

	    // 데이터를 파일에 저장하는 메서드
	    public void saveToFile() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
	            writer.write(name + "\n");
	            writer.write(balance + "\n");
	            System.out.println("데이터가 저장되었습니다.");
	        } catch (IOException e) {
	            System.out.println("데이터 저장 중 오류 발생: " + e.getMessage());
	        }
	    }

	    // 데이터를 파일에서 읽어오는 메서드
	    public static UserAccount loadFromFile() {
	        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
	            String name = reader.readLine();  // 첫 번째 줄: 이름
	            double balance = Double.parseDouble(reader.readLine()); // 두 번째 줄: 잔액
	            return new UserAccount(name, balance);
	        } catch (IOException e) {
	            System.out.println("기존 데이터를 찾을 수 없습니다. 새 계정을 생성합니다.");
	        }
	        // 기본 사용자 계정 생성
	        return new UserAccount("Default User", 0.0);
	    }

}
