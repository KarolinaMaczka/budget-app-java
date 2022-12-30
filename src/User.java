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
        this.homeAccount.addIncoming(income);
        this.incomings.add(income);
    }

    public void addExpense(Expense expense){
        this.homeAccount.addExpense(expense);
        this.expenses.add(expense);
    }
    public void addRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
        this.homeAccount.addExpense(e);
        this.expenses.add(e);}
    }
    public void removeIncoming(Income income) {
        this.homeAccount.removeIncoming(income);
        this.incomings.remove(income);
    }
    public void removeExpense(Expense expense) {
        this.homeAccount.removeExpense(expense);
        this.expenses.remove(expense);
    }
    public void removeRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
            this.homeAccount.removeExpense(e);
            this.expenses.remove(e);}
    }

    public double getPersonalBalance(LocalDate date) {
        double personalBalance=0;
        for(Expense e :expenses){
            if(e.getDate().isBefore(date)){
                personalBalance-=e.getAmount();
            }
        }
        for(Income i :incomings){
            if(i.getDate().isBefore(date)){
                personalBalance+=i.getAmount();
            }
        }
        return personalBalance;
    }


}
