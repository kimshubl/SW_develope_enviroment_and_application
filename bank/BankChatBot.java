package bank;

import java.util.Scanner;

public class BankChatBot {
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        String userInput, botResponse;

        while (true) {
        	System.out.println("1. 입출금");
            System.out.println("2. 예적금");
            System.out.println("3. 소비분석");
            
            System.out.print("User: ");
            userInput = scanner.nextLine();
            
            

            if (userInput.equalsIgnoreCase("1")) {
                botResponse = "입출금 기능은 1번입니다.";
            } else if (userInput.equalsIgnoreCase("2")) {
                botResponse = "예적금 기능은 2번입니다.";
            } else if (userInput.equalsIgnoreCase("3")) {
                botResponse = "소비분석 기능은 3번입니다.";
            } else if (userInput.equalsIgnoreCase("q")) {
            	break;
            } else {
                botResponse = "I don't understand what you mean by '" + userInput + "'.";
            }

            System.out.println(botResponse);
        }
    }
}
