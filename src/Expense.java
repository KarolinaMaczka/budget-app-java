import java.time.LocalDate;

public abstract class Expense {
    protected LocalDate date;
    protected double amount;
    protected String id;
    private static int counter;

    public Expense(double amount) {
        this.amount = -amount;
        this.date = LocalDate.now();
        this.id = "E" + counter;
        counter++;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", amount=" + amount +
                ", id='" + id + '\'' +
                '}';
    }
}
