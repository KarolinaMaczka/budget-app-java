package System;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class User implements Serializable {
//    User: pola: homeAccount personalBalance password login
//
//    metody: addIncoming addExpense addInvestment removeIncoming removeExpense removeInvestemnt getBalance

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

//    public List<Income> getIncomings() {
//        return incomings;
//    }
//
//    public List<Expense> getExpenses() {
//        return expenses;
//    }

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

    public double getAmountInvested() {
        System.out.println(this.investments);
        double amountt=0;
        System.out.println("halo pomocy");
        if(this.investments.isEmpty()){
            System.out.println("Jest pusta");
        }
        investments.forEach(System.out::println);
        for(Investment investment : investments) {
            System.out.println("halo pomocy2");
            System.out.println("its done");
            amountt += investment.getAmount();
        }
        return amountt;
    }

    public double getAmountInvestedDateRange(LocalDate start, LocalDate end) {
        double amount=0;
//        System.out.println();
        expenses.forEach(System.out::println);
        System.out.println("o");
        for(var inv : expenses) {
            if((inv.getDate().isBefore(end) && inv.getDate().isAfter(start)) || inv.getDate().isEqual(start) || inv.getDate().isEqual(end))
                amount += inv.getAmount();
//                amount ++;
        }
        return amount;
    }


    public void addInvestment(Investment investment) {
        this.investments.add(investment);
        this.homeAccount.addInvestment(investment);
    }

    public void addExpense(Expense expense){
        this.expenses.add(expense);
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
//        expenses.forEach(System.out::println);
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
