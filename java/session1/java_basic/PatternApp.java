import java.util.Scanner;

class PatternPrinter {

    public void printTriangle(int rows) {
        System.out.println("Triangle Pattern:");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    public void printSquare(int size) {
        System.out.println("Square Pattern:");
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}

public class PatternApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatternPrinter printer = new PatternPrinter();

        System.out.print("Enter size: ");
        int size = scanner.nextInt();

        printer.printTriangle(size);
        printer.printSquare(size);
    }
}