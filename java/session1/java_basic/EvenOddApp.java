import java.util.Scanner;

class NumberChecker {

    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}

public class EvenOddApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberChecker checker = new NumberChecker();

        System.out.print("Enter number: ");
        int number = scanner.nextInt();

        if (checker.isEven(number)) {
            System.out.println("Even");
        } else {
            System.out.println("Odd");
        }
    }
}