import java.util.ArrayList;
import java.util.List;

public class HomeAccount {
    private static List<Expense> expenses;
    private static List<Income> incomings;
    private static List<Investment> investments;
    private List<User> users;
    private double balance;

    public HomeAccount(){
        this.expenses = new ArrayList<>();
        this.incomings = new ArrayList<>();
        this.investments = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    private static void addIncoming(Income income){
        incomings.add(income);
    }

    private static void addExpense(Expense expense){
        expenses.add(expense);
    }

    private static void addInvestment(Investment investment){
        investments.add(investment);
    }

    private static void removeIncoming()
}
