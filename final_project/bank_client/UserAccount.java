package test3;

import java.io.*;
import java.util.*;

import test3.UserAccount;

public class UserAccount {
	static Clientwan CL = new Clientwan();
	private String name;       // 사용자 이름
    private double balance;    // 계좌 잔액
    private static final String FILE_PATH = "C:\\Users\\codbs\\Downloads\\SW_develope_enviroment_and_application-minchan_final_01 (1)\\SW_develope_enviroment_and_application-minchan_final_01\\bank\\user_data.txt"; // 사용자 데이터 파일 경로 //BankApp에 있는 users.txt 사용자 정보에다가 금액을 추가
    private static final String TRANSACTION_FILE_PATH = "C:\\Users\\codbs\\Downloads\\SW_develope_enviroment_and_application-minchan_final_01 (1)\\SW_develope_enviroment_and_application-minchan_final_01\\bank\\transaction_log.txt"; // 거래 기록 파일 경로

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
            Scanner scanner = new Scanner(System.in);
            System.out.print("입금 카테고리를 입력하세요 (예: 월급, 기타 소득): ");
            String category = scanner.nextLine();

            balance += amount;
            recordTransaction("입금", amount, category);
            System.out.println(amount + "원이 입금되었습니다. (카테고리: " + category + ")");
            CL.main();
        } else {
            System.out.println("입금 금액이 유효하지 않습니다.");
        }
    }

    // 출금 메서드
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("출금 카테고리를 입력하세요 (예: 생활비, 쇼핑): ");
            String category = scanner.nextLine();

            balance -= amount;
            recordTransaction("출금", amount, category); // 입력받은 카테고리를 기록
            System.out.println(amount + "원이 출금되었습니다. (카테고리: " + category + ")");
            CL.main();
        } else {
            System.out.println("잔액이 부족하거나 금액이 유효하지 않습니다.");
        }
    }


    // 사용자 데이터를 파일에 저장
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // 데이터를 Base64로 인코딩
            String base64EncodedName = Base64.getEncoder().encodeToString(name.getBytes("UTF-8"));
            String base64EncodedBalance = Base64.getEncoder().encodeToString(String.valueOf(balance).getBytes("UTF-8"));

            // 인코딩된 데이터를 파일에 저장
            writer.write(base64EncodedName + "\n");    // 이름 저장
            writer.write(base64EncodedBalance + "\n"); // 잔액 저장

            System.out.println("데이터가 Base64로 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }


    public static UserAccount loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            // Base64로 인코딩된 데이터를 읽기
            String base64EncodedName = reader.readLine();
            String base64EncodedBalance = reader.readLine();

            // 데이터를 Base64로 디코딩
            String decodedName = new String(Base64.getDecoder().decode(base64EncodedName), "UTF-8");
            double decodedBalance = Double.parseDouble(new String(Base64.getDecoder().decode(base64EncodedBalance), "UTF-8"));

            // 디코딩된 데이터를 UserAccount 객체로 반환
            return new UserAccount(decodedName, decodedBalance);
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return new UserAccount("Default User", 0.0);
    }


        


    // 거래 내역 기록
    public void recordTransaction(String type, double amount, String category) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE_PATH, true))) {
            String transaction = java.time.LocalDate.now() + ", " + type + ", " + amount + ", " + category;
            writer.write(transaction);
            writer.newLine();
            System.out.println("거래 기록이 저장되었습니다: " + transaction);
        } catch (IOException e) {
            System.out.println("거래 기록 저장 중 오류 발생: " + e.getMessage());
        }
    }

    // 소비 분석 (총 입금 및 출금 계산)
    public void analyzeSpending() {
        double totalDeposit = 0.0;
        double totalWithdraw = 0.0;

        System.out.println("\n=== 소비 분석 ===");
        System.out.println("입금 내역:");
        System.out.println("--------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);

                    if (type.equals("입금")) {
                        totalDeposit += amount;
                        System.out.printf("날짜: %s | 금액: %.2f원\n", parts[0], amount);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("거래 기록 분석 중 오류 발생: " + e.getMessage());
        }

        System.out.println("--------------------");
        System.out.println("출금 내역:");
        System.out.println("--------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);

                    if (type.equals("출금")) {
                        totalWithdraw += amount;
                        System.out.printf("날짜: %s | 금액: %.2f원\n", parts[0], amount);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("거래 기록 분석 중 오류 발생: " + e.getMessage());
        }

        System.out.println("--------------------");
        System.out.printf("총 입금액: %.2f원\n", totalDeposit);
        System.out.printf("총 출금액: %.2f원\n", totalWithdraw);
    }

    // 카테고리별 분석
    public void analyzeByCategory() {
        Map<String, Double> deposits = new HashMap<>();    // 입금 카테고리별 합계
        Map<String, Double> withdrawals = new HashMap<>(); // 출금 카테고리별 합계

        System.out.println("\n=== 카테고리별 분석 ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 거래 내역 한 줄을 읽어와 처리
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    String type = parts[1];          // 거래 유형: 입금/출금
                    double amount = Double.parseDouble(parts[2]); // 거래 금액
                    String category = parts[3];     // 카테고리

                    if (type.equals("입금")) {
                        // 입금 카테고리별 금액 합산
                        deposits.put(category, deposits.getOrDefault(category, 0.0) + amount);
                    } else if (type.equals("출금")) {
                        // 출금 카테고리별 금액 합산
                        withdrawals.put(category, withdrawals.getOrDefault(category, 0.0) + amount);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("카테고리 분석 중 오류 발생: " + e.getMessage());
        }

        // 입금 카테고리 출력
        System.out.println("\n입금 카테고리:");
        System.out.println("--------------------");
        for (Map.Entry<String, Double> entry : deposits.entrySet()) {
            System.out.printf("%s: %.2f원\n", entry.getKey(), entry.getValue());
        }

        // 출금 카테고리 출력
        System.out.println("--------------------");
        System.out.println("출금 카테고리:");
        System.out.println("--------------------");
        for (Map.Entry<String, Double> entry : withdrawals.entrySet()) {
            System.out.printf("%s: %.2f원\n", entry.getKey(), entry.getValue());
        }

        // 현재 계좌 금액 출력
        System.out.println("--------------------");
        System.out.printf("현재 계좌 잔액: %.2f원\n", balance);
    }



}