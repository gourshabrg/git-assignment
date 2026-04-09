public class DataTypeDemo {

    public static void main(String[] args) {

        // Primitive types
        int age = 25;
        double salary = 50000.5;
        char grade = 'A';
        boolean isEmployed = true;

        // Reference types
        String name = "Ravi";
        int[] marks = {80, 90, 85};

        System.out.println("Primitive Data Types:");
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Grade: " + grade);
        System.out.println("Employed: " + isEmployed);

        System.out.println("\nReference Data Types:");
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks[0] + ", " + marks[1] + ", " + marks[2]);
    }
}