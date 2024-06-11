package com.example.financetracker;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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