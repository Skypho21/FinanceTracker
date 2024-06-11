package com.example.financetracker;

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

