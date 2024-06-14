package com.example.financetracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class FinanceTrackerController {
    private FinanceDataStorage storage;

    @FXML
    private TextField amountField;
    @FXML
    private TextField categoryField;
    @FXML
    private Button addExpenseButton;
    @FXML
    private Button addIncomeButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button reportButton;

    @FXML
    private TableView<Expense> expenseTable;
    @FXML
    private TableColumn<Expense, Double> amountColumn;
    @FXML
    private TableColumn<Expense, String> categoryColumn;
    @FXML
    private TableColumn<Expense, LocalDate> dateColumn;

    private ObservableList<Expense> expenseList = FXCollections.observableArrayList();

    public void setFinanceDataStorage(FinanceDataStorage storage) {
        this.storage = storage;
        expenseList.setAll(storage.getExpenses());
        expenseTable.setItems(expenseList);
    }

    @FXML
    public void initialize() {
        addExpenseButton.setOnAction(event -> addExpense());
        addIncomeButton.setOnAction(event -> addIncome());
        deleteButton.setOnAction(event -> deleteSelected());
        reportButton.setOnAction(event -> generateReport());

        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        expenseTable.setItems(expenseList);
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
        expenseList.add(expense);

        // Clear the input fields after adding the expense
        amountField.clear();
        categoryField.clear();
    }

    private void addIncome() {
        String amountText = amountField.getText();

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        LocalDate date = LocalDate.now();
        Income income = new Income(amount, date);
        storage.addIncome(income);

        // Clear the input fields after adding the income
        amountField.clear();
        categoryField.clear();
    }

    private void deleteSelected() {
        Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            expenseList.remove(selectedExpense);
            storage.removeExpense(selectedExpense);
        }
    }

    private void generateReport() {
        // Implement report generation logic
        System.out.println("Generating report...");
    }
}
