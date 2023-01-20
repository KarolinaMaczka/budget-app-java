package System;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserAdult extends User{
    protected List<Investment> investments;
    protected HomeAccount homeAccount;

    public UserAdult(HomeAccount homeAccount, String password, String login,String firstName,String surname) {
        super(homeAccount, password, login,firstName,surname);
        investments = new ArrayList<>();
    }



    public void addInvestment(Investment investment){
        homeAccount.addInvestment(investment);
        investments.add(investment);
    }

    public void removeInvestment(Investment investment) {
        homeAccount.removeInvestment(investment);
        investments.remove(investment);
    }

    @Override
    public void addIncoming(Income income){
        homeAccount.addIncoming(income);
        incomings.add(income);
    }

    @Override
    public void addExpense(Expense expense){
        homeAccount.addExpense(expense);
        expenses.add(expense);
    }
    @Override
    public void addRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
            homeAccount.addExpense(e);
            expenses.add(e);}
    }
    @Override
    public void removeIncoming(Income income) {
        homeAccount.removeIncoming(income);
        incomings.remove(income);
    }
    @Override
    public void removeExpense(Expense expense) {
        homeAccount.removeExpense(expense);
        expenses.remove(expense);
    }
    @Override
    public void removeRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
            homeAccount.removeExpense(e);
            expenses.remove(e);}
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
