import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;

/**
 * Main class for the finance tracking application.
 * This class initializes the application, loads existing data, and handles user input.
 */
class App {
    public static void main(String[] args) {
        FinanceDataStorage storage = new FinanceDataStorage();
        storage.loadExpenses();
        storage.loadIncomes();
        UserInterface userInterface = new UserInterface(storage);

        while (true) { // Changed to an infinite loop, exit handled in UserInterface
            storage.displayData();
            userInterface.chooseFromMenu();
        }
    }
}


/**
 * Represents an expense with an amount, category, and date.
 */
class Expense {
    private double amount;
    private String category;
    private String date; //Gson does not support LocalDate

    /**
     * Constructs an Expense object.
     * @param amount   The amount of the expense.
     * @param category The category of the expense.
     * @param date     The date of the expense.
     */
    public Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public double getAmount() {
        return amount;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getDateAsLocalDate() {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

/**
 * Represents an income with an amount and date.
 */
class Income {
    private double amount;
    private String date; //Gson does not support LocalDate

    /**
     * Constructs an Income object.
     * @param amount The amount of the income.
     * @param date   The date of the income.
     */
    public Income(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public double getAmount() {
        return amount;
    }
     public LocalDate getDateAsLocalDate() {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

/**
 * Handles the storage of financial data, including loading and saving of expenses and incomes.
 */
class FinanceDataStorage {
    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();
    private static final String EXPENSES_FILE = "expenses.json";
    private static final String INCOME_FILE = "incomes.json";
    private Gson gson = new Gson();

    // Add getter methods for expenses and incomes
    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    /**
     * Adds an expense to the storage and saves it.
     * @param expense The expense to be added.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        // JSON save
        saveExpenses();
    }

    /**
     * Adds an income to the storage and saves it.
     * @param income The income to be added.
     */
    public void addIncome(Income income) {
        incomes.add(income);
        // JSON save
        saveIncomes();
    }

    /**
     * Saves the current list of expenses to a JSON file.
     * Serializes the list of Expense objects into JSON format and writes it to the specified file.
     * If an IOException occurs during this process, it prints the stack trace.
     */
    private void saveExpenses() {
        try (FileWriter writer = new FileWriter(EXPENSES_FILE)) {
            gson.toJson(expenses, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current list of incomes to a JSON file.
     * Serializes the list of Income objects into JSON format and writes it to the specified file.
     * If an IOException occurs during this process, it prints the stack trace.
     */
    private void saveIncomes() {
        try (FileWriter writer = new FileWriter(INCOME_FILE)) {
            gson.toJson(incomes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the current financial data in a formatted table.
     */
    public void displayData() {
        System.out.println("\nCurrent Expenses:");
        System.out.printf("%-16s %-20s %-10s%n", "Amount", "Category", "Date");
        expenses.stream()
            .sorted(Comparator.comparing(Expense::getDateAsLocalDate))
            .forEach(expense -> System.out.printf("$%-15.2f %-20s %-10s%n",
                expense.getAmount(),
                expense.getCategory(),
                expense.getDateAsLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));

        System.out.println("\nCurrent Incomes:");
        System.out.printf("%-16s %-10s%n", "Amount", "Date");
        incomes.stream()
            .sorted(Comparator.comparing(Income::getDateAsLocalDate))
            .forEach(income -> System.out.printf("$%-15.2f %-10s%n",
                income.getAmount(),
                income.getDateAsLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }
    
    /**
     * Loads the expenses from a JSON file.
     * If the file exists and is not empty, it reads the file and deserializes the JSON content into a list of Expense objects.
     * If the file does not exist or is empty, initializes an empty list and indicates that no existing data was found.
     */
    public void loadExpenses() {
        File file = new File(EXPENSES_FILE);
        if (file.exists() && file.length() > 0) {
        try (FileReader reader = new FileReader(file)) {
            expenses = gson.fromJson(reader, new com.google.gson.reflect.TypeToken<List<Expense>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace(); 
        } catch (Exception e) {
            e.printStackTrace();  
        }
        } else {
            expenses = new ArrayList<>(); 
            System.out.println("No existing expense data found.");
        }
    }

    /**
     * Loads the incomes from a JSON file.
     * If the file exists and is not empty, it reads the file and deserializes the JSON content into a list of Income objects.
     * If the file does not exist or is empty, initializes an empty list and indicates that no existing data was found.
     */
    public void loadIncomes() {
        File file = new File(INCOME_FILE);
        if (file.exists() && file.length() > 0) {
            try (FileReader reader = new FileReader(file)) {
                incomes = gson.fromJson(reader, new com.google.gson.reflect.TypeToken<List<Income>>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            incomes = new ArrayList<>(); 
            System.out.println("No existing income data found.");
        }
    }
    
}

class ReportGenerator {
    private FinanceDataStorage storage;

    public ReportGenerator(FinanceDataStorage storage) {
        this.storage = storage;
    }

    public void generateReport() {
        // Implement the logic for generating and displaying the report
        System.out.println("\n--- Financial Report ---");
        // Example report content (customize as needed)
        System.out.println("Total Income: " + calculateTotalIncome());
        System.out.println("Total Expenses: " + calculateTotalExpenses());
        // Add more report details here
    }

    private double calculateTotalIncome() {
        return storage.getIncomes().stream().mapToDouble(Income::getAmount).sum();
    }

    private double calculateTotalExpenses() {
        return storage.getExpenses().stream().mapToDouble(Expense::getAmount).sum();
    }
}

/**
 * Handles user interface for input and interaction.
 */class UserInterface {
    private Scanner scanner;
    private FinanceDataStorage storage;
    private ReportGenerator reportGenerator;

    public UserInterface(FinanceDataStorage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
        this.reportGenerator = new ReportGenerator(storage);
    }

    public void chooseFromMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("I - Enter income or expense.");
        System.out.println("R - View a report.");
        System.out.println("Q - Quit.");
        System.out.print("Enter your choice: ");

        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Input is empty, please enter an option.");
            return;
        }
        char option = input.trim().toLowerCase().charAt(0);

        switch (option) {
            case 'i':
                getInput();
                break;
            case 'r':
                reportGenerator.generateReport();
                break;
            case 'q':
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option, please try again.");
                break;
            }
        } 
   /**
     * Gets financial data input from the user.
     */
    private void getInput() {
    System.out.print("Enter an amount (ex. -5.00 for expense or 5.00 for income): ");
    double amount;
    try {
        amount = scanner.nextDouble();
    } catch (Exception e) {
        System.out.println("Invalid input. Please enter a numeric value.");
        scanner.nextLine(); // Clear buffer
        return;
    }
    scanner.nextLine(); // Consume the leftover newline

    LocalDate date = LocalDate.now(); 

    if (amount < 0) {
        System.out.print("Enter a category for the expense: ");
        String category = scanner.nextLine();
        Expense expense = new Expense(Math.abs(amount), category, date);
        storage.addExpense(expense);
    } else if (amount > 0) {
        Income income = new Income(amount, date);
        storage.addIncome(income);
    } else {
        System.out.println("Amount cannot be zero.");
    }
}
 }

