import java.time.LocalDate;

public class UserChild extends User{
    public UserChild(HomeAccount homeAccount, int personalBalance, String password, String login) {
        super(homeAccount, password, login);
    }

    @Override
    public void addIncoming(Income income) {
        super.addIncoming(income);
    }

    @Override
    public void addExpense(Expense expense) {
        super.addExpense(expense);
    }

    @Override
    public void addRecurringExpense(RecurringExpense expense) {
        super.addRecurringExpense(expense);
    }

    @Override
    public void removeIncoming(Income income) {
        super.removeIncoming(income);
    }

    @Override
    public void removeExpense(Expense expense) {
        super.removeExpense(expense);
    }

    @Override
    public void removeRecurringExpense(RecurringExpense expense) {
        super.removeRecurringExpense(expense);
    }

    @Override
    public double getPersonalBalance(LocalDate date) {
        return super.getPersonalBalance(date);
    }
}
