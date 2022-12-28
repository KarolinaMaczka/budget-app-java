public class RecurringExpense extends Expense {
    public RecurringExpense(double amount) {
        super(amount);
        this.id = "R" + this.id;
    }
}
