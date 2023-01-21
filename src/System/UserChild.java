package System;

import java.time.LocalDate;

public class UserChild extends User{
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
        this.expenses.add(expense);
    }
    @Override
    public void addRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
            this.expenses.add(e);}
    }
    @Override
    public void removeIncoming(Income income) {
        this.incomings.remove(income);
    }
    @Override
    public void removeExpense(Expense expense) {
        this.expenses.remove(expense);
    }
    @Override
    public void removeRecurringExpense(RecurringExpense expense){
        for (Expense e : expense.pastExpenses()){
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

    @Override
    public String toString() {
        return "UserChild{" +
                "password='" + password + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
