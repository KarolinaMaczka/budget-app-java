import java.time.LocalDate;

public class Income {
    private LocalDate date;
    private double amount;
    private String id;
    private static int counter;

    public Income(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
        this.id = "I" + counter;
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
