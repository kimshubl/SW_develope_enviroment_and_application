package bank;
import java.util.Scanner;

public class SavingsCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 예적금 계산 프로그램 ===");
        System.out.println("1. 예금 계산 (정기 예금)");
        System.out.println("2. 적금 계산 (정기 적립식 저축)");
        System.out.print("선택 (1 또는 2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                calculateFixedDeposit(scanner);
                break;
            case 2:
                calculateRecurringDeposit(scanner);
                break;
            default:
                System.out.println("올바른 옵션을 선택해주세요.");
        }

        scanner.close();
    }

    // 정기 예금 계산 메서드
    private static void calculateFixedDeposit(Scanner scanner) {
        System.out.print("초기 예치 금액을 입력하세요: ");
        double principal = scanner.nextDouble();

        System.out.print("연 이자율 (%)을 입력하세요: ");
        double annualRate = scanner.nextDouble();

        System.out.print("예치 기간 (연)을 입력하세요: ");
        int years = scanner.nextInt();

        double maturityAmount = principal * Math.pow(1 + annualRate / 100, years);

        System.out.printf("예치 금액: %.2f원\n", principal);
        System.out.printf("만기 금액: %.2f원\n", maturityAmount);
        System.out.printf("총 이자 수익: %.2f원\n", maturityAmount - principal);
    }

    // 정기 적립식 저축 계산 메서드
    private static void calculateRecurringDeposit(Scanner scanner) {
        System.out.print("매월 적립 금액을 입력하세요: ");
        double monthlyDeposit = scanner.nextDouble();

        System.out.print("연 이자율 (%)을 입력하세요: ");
        double annualRate = scanner.nextDouble();

        System.out.print("적립 기간 (개월)을 입력하세요: ");
        int months = scanner.nextInt();

        double monthlyRate = annualRate / 12 / 100;
        double maturityAmount = 0;

        for (int i = 0; i < months; i++) {
            maturityAmount += monthlyDeposit * Math.pow(1 + monthlyRate, months - i);
        }

        System.out.printf("총 납입 금액: %.2f원\n", monthlyDeposit * months);
        System.out.printf("만기 금액: %.2f원\n", maturityAmount);
        System.out.printf("총 이자 수익: %.2f원\n", maturityAmount - (monthlyDeposit * months));
    }
}
