import java.time.LocalDate;

public class Income {
    protected LocalDate date;
    protected double amount;
    protected String id;
    private static int counter;

    public Income(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
        this.id = "I" + counter;
        counter++;
    }
}
