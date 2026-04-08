import java.util.Scanner;

public class MultiplicationTablePrinter {

    private static final int LIMIT = 10;

    public static void printTable(int number) {
        for (int i = 1; i <= LIMIT; i++) {
            System.out.println(number + " x " + i + " = " + (number * i));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int inputNumber = scanner.nextInt();

        printTable(inputNumber);

        scanner.close();
    }
}