package System;

import java.io.Serializable;
import java.time.LocalDate;

public class UserChild extends User implements Serializable {
    public UserChild(HomeAccount homeAccount, String password, String login,String firstName,String surname) {
        super(homeAccount, password, login,firstName,surname);
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
}
