import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        FinanceDataStorage storage = new FinanceDataStorage();
        UserInterface userInterface = new UserInterface(storage);

        userInterface.getInput();

        //testing
        //double a = num + 10.0;
        //System.out.print(a);
}}

class Expense {
    private double amount;
    private String category;
    private LocalDate date;

    public Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getDate() {
        return date;
    }
}

class Income {
    private double amount;
    private LocalDate date;

    public Income(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
}

class FinanceDataStorage {
    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
        // JSON save
    }

    public void addIncome(Income income) {
        incomes.add(income);
        // JSON save
    }
    
    // Load and save data (JSON, XML, or database)
}

class ReportGenerator {
    // Methods to generate different financial reports
}

class UserInterface {
    private Scanner scanner;
    private FinanceDataStorage storage;

    public UserInterface(FinanceDataStorage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
    }

    protected void getInput() {
        System.out.print("Enter an amount (-50.00 or 50.00): $");
        double amount = scanner.nextDouble();
        LocalDate date = LocalDate.now(); 
        String category = ""; 

        if (amount < 0) {
            // It's an expense
            Expense expense = new Expense(amount, category, date);
            storage.addExpense(expense);
        } else {
            // It's an income
            Income income = new Income(amount, date);
            storage.addIncome(income);
        }
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }   
}

