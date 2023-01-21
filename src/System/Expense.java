package System;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    private LocalDate date;
    private double amount;
    private String id;
    private CategoryOfExpense categoryOfExpense;
    private boolean isRecurring;
    private static int counter;

    public Expense(double amount, CategoryOfExpense category, boolean isRecurring) {
        this.amount = -amount;
        this.date = LocalDate.now();
        this.isRecurring = isRecurring;
        this.categoryOfExpense = category;
        this.id = isRecurring ? "rE" : "E";
        this.id = this.id + counter;
        counter++;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public CategoryOfExpense getCategoryOfExpense() {
        return categoryOfExpense;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  date +
                " " + amount +
                " " + categoryOfExpense.name();
    }
}
