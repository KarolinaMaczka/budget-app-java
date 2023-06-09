package System;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class User implements Serializable {

    protected HomeAccount homeAccount;
    protected String password;
    protected String login;
    protected String firstName;
    protected String surname;
    protected List<Expense> expenses;
    protected List<Income> incomings;
    protected List<Investment> investments;


    public User(HomeAccount homeAccount, String password, String login,String firstName,String surname) {
        this.homeAccount = homeAccount;
        this.homeAccount.addUser(this);
        this.password = password;
        this.login = login;
        this.expenses = new ArrayList<>();
        this.incomings = new ArrayList<>();
        this.investments = new ArrayList<>();
        this.firstName=firstName;
        this.surname=surname;
    }


    public String getLogin() {
        return login;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public void addIncoming(Income income){
    }

    public HomeAccount getHomeAccount() {
        return homeAccount;
    }

    public  double getAmountInvested() {

        System.out.println(this.investments);
        double amountt = 0;
        if (this.investments.isEmpty()) {
            System.out.println("Jest pusta");
        }
        investments.forEach(System.out::println);
        for (Investment investment : investments) {
            amountt += investment.getAmount();
        }
        return amountt;

    }

    public int getInvestitionsSize(){
        return investments.size();
    }

    public double getAmountInvestedDateRange(LocalDate start, LocalDate end) {
        double amount=0;

        for(var inv : this.investments) {
                amount += inv.getAmount();
        }
        return amount;
    }

    public double investedInTotal() {
        double res=0;

        for(var i : investments) {
            res += i.getAmount();
        }

        return res;
    }


    public void addInvestment(Investment investment) {
    }

    public void addToExisingInvestment(Investment investment, double am){

    }

    public void addExpense(Expense expense){
        this.expenses.add(expense);
        this.homeAccount.addExpense(expense);
    }
    public void addRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.getAddToExpenseList()){
            this.homeAccount.addExpense(e);
            this.expenses.add(e);}
    }
    public void removeIncoming(Income income) {
    }
    public void removeExpense(Expense expense) {
    }
    public void removeRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.allExpenses()){
            this.homeAccount.removeExpense(e);
            this.expenses.remove(e);}
    }


    public double getPersonalBalance(LocalDate date) {
        double personalBalance=0;
        return personalBalance;
    }

    public double getPersonalBalance() {
        double b = 0;
        for(var e :expenses) {
            b += e.getAmount();
        }
        for(var i : incomings) {
            b += i.getAmount();
        }
        return b;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Income> getIncomings() {
        return incomings;
    }

    public List<Investment> getInvestments() {return investments;}

    public double getPersonalBalanceDateRange(LocalDate start, LocalDate end) {
        double personalBalance=0;
        for(Expense e :expenses){
            if((e.getDate().isBefore(end) && e.getDate().isAfter(start)) || e.getDate().isEqual(start) || e.getDate().isEqual(end)){
                personalBalance+=e.getAmount();
            }
        }
        for(Income i :incomings){
            if((i.getDate().isBefore(end) && i.getDate().isAfter(start)) || i.getDate().isEqual(start) || i.getDate().isEqual(end)){
                personalBalance+=i.getAmount();
            }
        }


        for (Investment investment : investments) {

            if((investment.getDate().isBefore(end) && investment.getDate().isAfter(start)) || investment.getDate().isEqual(start) || investment.getDate().isEqual(end))
                personalBalance += investment.getAmount();
        }

        return personalBalance;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
