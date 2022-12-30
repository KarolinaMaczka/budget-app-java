import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class User {
//    User: pola: homeAccount personalBalance password login
//
//    metody: addIncoming addExpense addInvestment removeIncoming removeExpense removeInvestemnt getBalance

    protected HomeAccount homeAccount;
    protected String password;
    protected String login;
    protected List<Expense> expenses;
    protected List<Income> incomings;


    public User(HomeAccount homeAccount, String password, String login) {
        this.homeAccount = homeAccount;
        this.homeAccount.addUser(this);
        this.password = password;
        this.login = login;
        this.expenses = new ArrayList<>();
        this.incomings = new ArrayList<>();
    }

    public void addIncoming(Income income){
    }

    public void addExpense(Expense expense){
    }
    public void addRecurringExpense(RecurringExpense expense){
    }
    public void removeIncoming(Income income) {
    }
    public void removeExpense(Expense expense) {
    }
    public void removeRecurringExpense(RecurringExpense expense){
    }

    public double getPersonalBalance(LocalDate date) {
        double personalBalance=0;
        return personalBalance;
    }


}
