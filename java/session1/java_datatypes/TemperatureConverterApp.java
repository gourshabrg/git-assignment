import java.util.Scanner;

public class TemperatureConverterApp {

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.print("Enter Celsius: ");
            double celsius = scanner.nextDouble();

            double result = celsiusToFahrenheit(celsius);
            System.out.println("Fahrenheit: " + result);

        } else if (choice == 2) {
            System.out.print("Enter Fahrenheit: ");
            double fahrenheit = scanner.nextDouble();

            double result = fahrenheitToCelsius(fahrenheit);
            System.out.println("Celsius: " + result);

        } else {
            System.out.println("Invalid choice");
        }
    }
}