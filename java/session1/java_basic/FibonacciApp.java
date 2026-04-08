import java.util.Scanner;

class FibonacciGenerator {

    public void printFibonacci(int limit) {
        int first = 0, second = 1;

        System.out.print("Fibonacci: " + first + " " + second);

        for (int i = 2; i < limit; i++) {
            int next = first + second;
            System.out.print(" " + next);

            first = second;
            second = next;
        }
    }
}

public class FibonacciApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FibonacciGenerator generator = new FibonacciGenerator();

        System.out.print("Enter limit: ");
        int limit = scanner.nextInt();

        generator.printFibonacci(limit);
    }
}