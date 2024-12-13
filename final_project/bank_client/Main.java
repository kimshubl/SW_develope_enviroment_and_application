package bank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	BankChatBot BCB = new BankChatBot();
        Scanner scanner1 = new Scanner(System.in);

        // 파일에서 사용자 데이터 로드
        UserAccount user = UserAccount.loadFromFile();
        

        System.out.println("안녕하세요, " + user.getName() + "님!");

        while (true) {
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

            int choice = scanner1.nextInt();

            switch (choice) {
                case 1: // 잔액 조회
                    System.out.println("현재 잔액: " + user.getBalance() + "원");
                    break;

                case 2: // 입금
                    System.out.print("입금할 금액: ");
                    double depositAmount = scanner1.nextDouble();
                    scanner1.nextLine(); 
                    user.deposit(depositAmount);
                    user.saveToFile(); // 변경된 데이터 저장
                    break;

                case 3: // 출금
                    System.out.print("출금할 금액: ");
                    double withdrawAmount = scanner1.nextDouble();
                    scanner1.nextLine(); 
                    user.withdraw(withdrawAmount);
                    user.saveToFile(); // 변경된 데이터 저장
                    break;

                case 4: // 소비 분석
                    user.analyzeSpending();
                    break;

                case 5: // 카테고리별 분석
                    user.analyzeByCategory();
                    break;
                    
                case 6:
                	BCB.main();
                	break;

                case 7: // 종료
                    System.out.println("프로그램을 종료합니다.");
                    scanner1.close();
                    return;
                    
                

                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }

	


	
}
