import java.io.*;
import java.util.*;

class ExpenseTracker {

    // Data structure to store expenses
    private static List<Expense> expenses = new ArrayList<>();
    private static Map<String, Double> categorySummation = new HashMap<>();

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the Expense Tracker!");

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Category-wise Summation");
            System.out.println("4. Save Data");
            System.out.println("5. Load Data");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewCategorySummation();
                    break;
                case 4:
                    saveData();
                    break;
                case 5:
                    loadData();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting the Expense Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    // Add an expense
    private static void addExpense(Scanner scanner) {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter the category: ");
        String category = scanner.nextLine();

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Expense expense = new Expense(date, category, amount);
        expenses.add(expense);

        // Update category-wise summation
        categorySummation.put(category, categorySummation.getOrDefault(category, 0.0) + amount);

        System.out.println("Expense added successfully!");
    }

    // View all expenses
    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        }

        System.out.println("\nExpenses:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    // View category-wise summation
    private static void viewCategorySummation() {
        if (categorySummation.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        }

        System.out.println("\nCategory-wise Summation:");
        for (Map.Entry<String, Double> entry : categorySummation.entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    // Save data to a file
    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("expenses.dat"))) {
            oos.writeObject(expenses);
            oos.writeObject(categorySummation);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data from a file
    @SuppressWarnings("unchecked")
    private static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("expenses.dat"))) {
            expenses = (List<Expense>) ois.readObject();
            categorySummation = (Map<String, Double>) ois.readObject();
            System.out.println("Data loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}

// Expense class
class Expense implements Serializable {
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Date: %s, Category: %s, Amount: %.2f", date, category, amount);
    }
}
