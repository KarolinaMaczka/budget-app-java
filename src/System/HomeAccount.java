package System;

        import java.io.*;
        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

public class HomeAccount implements Serializable {
    private List<Expense> expenses;
    private List<Income> incomings;
    private List<Investment> investments;
    private List<User> users;
    private double balance;

    public HomeAccount(){
        this.expenses = new ArrayList<>();
        this.incomings = new ArrayList<>();
        this.investments = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addIncoming(Income income){
        this.incomings.add(income);
    }

    public void addExpense(Expense expense){
        this.expenses.add(expense);
    }

    public void addInvestment(Investment investment){
        this.investments.add(investment);
    }

    public void removeIncoming(Income incoming) { this.incomings.remove(incoming); }
    public void removeExpense(Expense expense) { this.expenses.remove(expense); }

    public void removeInvestment(Investment investment) { this.investments.remove(investment); }

    public void updateBalance() {
        balance = 0;
        for(var e : expenses) {
            balance -= e.getAmount();
        }
        for(var in : incomings) {
            balance -= in.getAmount();
        }
    }

    public double getBalance() {
        updateBalance();
        return balance;
    }

    public double getBalanceDateRange(LocalDate start, LocalDate end) {
        double personalBalance=0;
        for(Expense e :expenses){
            if(e.getDate().isBefore(end) & e.getDate().isAfter(start)){
                personalBalance-=e.getAmount();
            }
        }
        for(Income i :incomings){
            if(i.getDate().isBefore(end) & i.getDate().isAfter(start)){
                personalBalance+=i.getAmount();
            }
        }
        return personalBalance;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User containsLogin(String login){
        Optional ans = this.users.stream().filter(o -> o.getLogin().equals(login)).findFirst();
        if (ans.isPresent()){
            return (User) ans.get();
        }else{
            return null;
        }
    }

}
