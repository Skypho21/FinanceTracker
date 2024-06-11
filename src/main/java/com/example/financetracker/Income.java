package com.example.financetracker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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