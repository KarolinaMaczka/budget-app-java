public class NonRecurringExpense extends Expense {

    public NonRecurringExpense(double amount) {
        super(amount);
        this.id = "nR" + this.id;
    }
}
