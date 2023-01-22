package System;

import java.io.Serializable;
import java.time.LocalDate;

public class Investment implements Serializable {
    private LocalDate date;
    private double amount;
    private String id;
    private static int counter;

    protected String name;

    public Investment(double amount, String name) {
        this.amount = amount;
        this.date = LocalDate.now();
        this.id = "Inv" + counter;
        counter++;
        this.name  = name;
    }

    public String getName() {return name;}
    public LocalDate getDate() {
        return date;
    }
    public void addToAmount(double add){
        this.amount += add;
    }
    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }
}
