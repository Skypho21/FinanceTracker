package com.example.financetracker;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FinanceTrackerController {
    private FinanceDataStorage storage;

    @FXML
    private TextField amountField;
    @FXML
    private TextField categoryField;
    @FXML
    private Button addButton;

    public void setFinanceDataStorage(FinanceDataStorage storage) {
        this.storage = storage;
    }

    @FXML
    public void initialize() {
        addButton.setOnAction(event -> addExpense());
    }

    private void addExpense() {
        String amountText = amountField.getText();
        String category = categoryField.getText();

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        if (category.isEmpty()) {
            System.out.println("Category cannot be empty");
            return;
        }

        LocalDate date = LocalDate.now();
        Expense expense = new Expense(amount, category, date);
        storage.addExpense(expense);
    }
}
