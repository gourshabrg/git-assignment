import java.util.Scanner;

public class LinearSearchApp {

    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int size = scanner.nextInt();

        int[] numbers = new int[size];

        System.out.println("Enter elements:");
        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.print("Enter element to search: ");
        int target = scanner.nextInt();

        int result = linearSearch(numbers, target);

        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}