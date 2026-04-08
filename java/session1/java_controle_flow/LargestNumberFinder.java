
import java.util.Scanner;

public class LargestNumberFinder {

    public static int findLargest(int first, int second, int third) {
        if (first >= second && first >= third) return first;
        else if (second >= first && second >= third) return second;
        else return third;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter three numbers: ");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();

        int largest = findLargest(a, b, c);
        System.out.println("Largest number is: " + largest);

        scanner.close();
    }
}