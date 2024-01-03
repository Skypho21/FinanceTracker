import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UserInterface user = new UserInterface();
        user.getInput();
    }
}

class Expense {
    private double amount;
    private String category;
    private LocalDate date;
    // Constructor, getters, setters
}

class Income {
    private double amount;
    private LocalDate date;
    // Constructor, getters, setters
}

class FinanceDataStorage {
    
    // Methods to load and save data (JSON, XML, or database)
}

class ReportGenerator {
    // Methods to generate different financial reports
}

class UserInterface {
    protected void getInput()
    {
        Scanner scan = new Scanner(System.in);
        int amount = scan.nextInt();
        //Get info 
        //Do calc
        //Save
        //Or do the calculation when called rather than pre-emptively
    }
    // Methods to handle user input and display outputs
}
