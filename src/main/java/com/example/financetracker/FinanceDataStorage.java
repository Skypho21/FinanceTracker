package com.example.financetracker;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FinanceDataStorage {
    private ObservableList<Expense> expenses;
    private ObservableList<Income> incomes;
    private static final String EXPENSES_FILE = "src/main/resources/expenses.json";
    private static final String INCOME_FILE = "src/main/resources/incomes.json";
    private Gson gson = new Gson();

    public FinanceDataStorage() {
        expenses = FXCollections.observableArrayList();
        incomes = FXCollections.observableArrayList();
        loadExpenses();
        loadIncomes();
    }

    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    public ObservableList<Income> getIncomes() {
        return incomes;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    public void addIncome(Income income) {
        incomes.add(income);
        saveIncomes();
    }

    private void saveExpenses() {
        try (FileWriter writer = new FileWriter(EXPENSES_FILE)) {
            gson.toJson(expenses, writer);
            System.out.println("Expenses saved to " + new File(EXPENSES_FILE).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveIncomes() {
        try (FileWriter writer = new FileWriter(INCOME_FILE)) {
            gson.toJson(incomes, writer);
            System.out.println("Incomes saved to " + new File(INCOME_FILE).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadExpenses() {
        File file = new File(EXPENSES_FILE);
        if (file.exists() && file.length() > 0) {
            try (FileReader reader = new FileReader(file)) {
                List<Expense> expenseList = gson.fromJson(reader, new com.google.gson.reflect.TypeToken<List<Expense>>() {}.getType());
                expenses.addAll(expenseList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No existing expense data found.");
        }
    }

    public void loadIncomes() {
        File file = new File(INCOME_FILE);
        if (file.exists() && file.length() > 0) {
            try (FileReader reader = new FileReader(file)) {
                List<Income> incomeList = gson.fromJson(reader, new com.google.gson.reflect.TypeToken<List<Income>>() {}.getType());
                incomes.addAll(incomeList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No existing income data found.");
        }
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
        saveExpenses();
    }
}
