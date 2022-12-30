import java.util.ArrayList;
import java.util.List;

public class HomeAccount {
    private static List<Expense> expenses;
    private static List<Income> incomings;
    private static List<Investment> investments;
    private static List<User> users;
    private static double balance;

    public HomeAccount(){
        expenses = new ArrayList<>();
        incomings = new ArrayList<>();
        investments = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static void addIncoming(Income income){
        incomings.add(income);
    }

    public static void addExpense(Expense expense){
        expenses.add(expense);
    }

    public static void addInvestment(Investment investment){
        investments.add(investment);
    }

    public static void removeIncoming(Income incoming) { incomings.remove(incoming); }
    public static void removeExpense(Expense expense) { expenses.remove(expense); }

    public static void removeInvestment(Investment investment) { investments.remove(investment); }
    public static double getBalance() { return balance; }

    public static void addUser(User user){
        users.add(user);

//        if(type == "Adult"){
//            users.add(new User());
//        }
//        if(type == "Child"){
//            users.add(new User());
//        }
    }

    public static void removeUser(User user){
        users.remove(user);
    }

}
