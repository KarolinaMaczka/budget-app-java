import java.time.LocalDate;

public class Investment {
    protected LocalDate date;
    protected double amount;
    protected String id;
    private static int counter;

    public Investment(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
        this.id = "Inv" + counter;
        counter++;
    }
}
