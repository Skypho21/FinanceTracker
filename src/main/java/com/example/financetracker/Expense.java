package com.example.financetracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an expense with an amount, category, and date.
 */
public class Expense {
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

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
