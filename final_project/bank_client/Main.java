package test3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	BankChatBot BCB = new BankChatBot();
        Scanner scanner1 = new Scanner(System.in);

        // 파일에서 사용자 데이터 로드
        UserAccount user = UserAccount.loadFromFile();
        

        System.out.println("안녕하세요, " + user.getName() + "님!");

        while (true) {
            try {
                // 메뉴 표시
                System.out.println("\n=== 은행 앱 메뉴 ===");
                System.out.println("1. 잔액 조회");
                System.out.println("2. 입금");
                System.out.println("3. 출금");
                System.out.println("4. 소비 분석");
                System.out.println("5. 카테고리별 분석");
                System.out.println("6. 챗봇");
                System.out.println("7. 종료");
                System.out.print("선택: ");

                int choice;
                while (true) {
                    System.out.print("선택: ");
                    if (!scanner1.hasNextInt()) {
                        System.out.println("숫자를 입력하세요.");
                        scanner1.next(); // 잘못된 입력 스킵
                        continue;
                    }
                    choice = scanner1.nextInt();
                    break;
                }

                switch (choice) {
                    case 1: // 잔액 조회
                        System.out.println("현재 잔액: " + user.getBalance() + "원");
                        break;

                    case 2: // 입금
                        System.out.print("입금할 금액: ");
                        if (!scanner1.hasNextDouble()) {
                            System.out.println("유효한 금액을 입력하세요.");
                            scanner1.next();
                            continue;
                        }
                        double depositAmount = scanner1.nextDouble();
                        user.deposit(depositAmount);
                        user.saveToFile(); // 변경된 데이터 저장
                        break;

                    case 3: // 출금
                        System.out.print("출금할 금액: ");
                        if (!scanner1.hasNextDouble()) {
                            System.out.println("유효한 금액을 입력하세요.");
                            scanner1.next();
                            continue;
                        }
                        double withdrawAmount = scanner1.nextDouble();
                        user.withdraw(withdrawAmount);
                        user.saveToFile(); // 변경된 데이터 저장
                        break;

                    case 4: // 소비 분석
                        user.analyzeSpending();
                        break;

                    case 5: // 카테고리별 분석
                        user.analyzeByCategory();
                        break;

                    case 6: // 챗봇 호출
                        BCB.main();
                        break;

                    case 7: // 종료
                        System.out.println("프로그램을 종료합니다.");
                        scanner1.close();
                        return;

                    default:
                        System.out.println("올바른 번호를 입력하세요.");
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
                scanner1.nextLine(); // 잘못된 입력을 버퍼에서 제거
            }
        }


	
    }
}
