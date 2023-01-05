package System;

import java.time.LocalDate;

public class Investment {
    private LocalDate date;
    private double amount;
    private String id;
    private static int counter;

    public Investment(double amount) {
        this.amount = -amount;
        this.date = LocalDate.now();
        this.id = "Inv" + counter;
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
}
