import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserAdult extends User{
    protected List<Investment> investments;

    public UserAdult(HomeAccount homeAccount, String password, String login) {
        super(homeAccount, password, login);
        this.investments = new ArrayList<>();
    }


    public void addInvestment(Investment investment){
        this.homeAccount.addInvestment(investment);
        this.investments.add(investment);
    }

    public void removeInvestment(Investment investment) {
        this.homeAccount.removeInvestment(investment);
        this.investments.remove(investment);
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
