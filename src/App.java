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

public class App {
    public static void main(String[] args) {
        FinanceDataStorage storage = new FinanceDataStorage();
        storage.loadExpenses();
        storage.loadIncomes();
        Scanner scanner = new Scanner(System.in);
        UserInterface userInterface = new UserInterface(storage);

        boolean keepRunning = true;
        while (keepRunning) {
            storage.displayData();
            userInterface.getInput();
            System.out.print("Continue? (yes/no): ");
            String response = scanner.nextLine();
            if ("no".equalsIgnoreCase(response)) {
                keepRunning = false;
            }
        }

        scanner.close(); 
    }
}



class Expense {
    private double amount;
    private String category;
    private String date; //Gson does not support LocalDate

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

class Income {
    private double amount;
    private String date; //Gson does not support LocalDate


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

class FinanceDataStorage {
    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();
    private static final String EXPENSES_FILE = "expenses.json";
    private static final String INCOME_FILE = "incomes.json";
    private Gson gson = new Gson();

    public void addExpense(Expense expense) {
        expenses.add(expense);
        // JSON save
        saveExpenses();
    }

    public void addIncome(Income income) {
        incomes.add(income);
        // JSON save
        saveIncomes();
    }

     private void saveExpenses() {
        try (FileWriter writer = new FileWriter(EXPENSES_FILE)) {
            gson.toJson(expenses, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveIncomes() {
        try (FileWriter writer = new FileWriter(INCOME_FILE)) {
            gson.toJson(incomes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    
}

class UserInterface {
    private Scanner scanner;
    private FinanceDataStorage storage;

    public UserInterface(FinanceDataStorage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
    }

    public void getInput() {
        System.out.print("Enter an amount (ex. -5.00 or 5.00): ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the leftover newline

        LocalDate date = LocalDate.now(); 
        System.out.print("\n");

        if (amount < 0) {
            System.out.print("Enter a category: ");
            String category = scanner.nextLine(); 
            Expense expense = new Expense(amount, category, date);
            storage.addExpense(expense);
        } else {
            Income income = new Income(amount, date);
            storage.addIncome(income);
        }
    }

}


