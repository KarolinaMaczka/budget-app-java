package System;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserAdult extends User implements Serializable {
    protected List<Investment> investments;
    protected HomeAccount homeAccount;

    public UserAdult(HomeAccount homeAccount, String password, String login,String firstName,String surname) {
        super(homeAccount, password, login,firstName,surname);
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
    public void addIncoming(Income income){
        this.homeAccount.addIncoming(income);
        this.incomings.add(income);
    }

    @Override
    public void addExpense(Expense expense){
        this.homeAccount.addExpense(expense);
        this.expenses.add(expense);
    }
    @Override
    public void addRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
            this.homeAccount.addExpense(e);
            this.expenses.add(e);}
    }
    @Override
    public void removeIncoming(Income income) {
        this.homeAccount.removeIncoming(income);
        this.incomings.remove(income);
    }
    @Override
    public void removeExpense(Expense expense) {
        this.homeAccount.removeExpense(expense);
        this.expenses.remove(expense);
    }
    @Override
    public void removeRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
            this.homeAccount.removeExpense(e);
            this.expenses.remove(e);}
    }

    @Override
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


    //TODO
    // do usunięcia
    @Override
    public String toString() {
        return "UserAdult{" +
                "password='" + password + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

}
