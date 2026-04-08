import java.util.Scanner;

public class ArraySorterApp {

    public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean isSwapped;

        for (int i = 0; i < n - 1; i++) {
            isSwapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isSwapped = true;
                }
            }

            if (!isSwapped) break; // optimization
        }
    }

    public static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
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

        bubbleSort(numbers);

        System.out.print("Sorted Array: ");
        printArray(numbers);
    }
}