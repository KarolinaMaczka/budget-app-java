package System;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserAdult extends User{
    protected List<Investment> investments;

    public UserAdult(HomeAccount homeAccount, String password, String login,String firstName,String surname) {
        super(homeAccount, password, login,firstName,surname);
        this.investments = new ArrayList<>();
    }

    public List<Investment> getInvestments() {return investments;}

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
    public double getAmountInvested() {
            double amountt = 0;
            System.out.println("rozmiarxx: "+ this.investments.size());
            for(Investment investment: investments){
                amountt+=investment.getAmount();
                System.out.println("Kwota: "+ amountt);
            }

            //System.out.println(this.investments);
            //double amountt=0;
            //System.out.println("halo pomocy");
            //if(this.investments.isEmpty()){
            // System.out.println("Jest pusta");
            //}
            //investments.forEach(System.out::println);
            // for(Investment investment : investments) {
            //    System.out.println("halo pomocy2");
            //   System.out.println("its done");
            //   amountt += investment.getAmount();
            //}
            //return amountt;
            return amountt;

    }

    public void addToExisingInvestment(Investment investment, double am){
        this.homeAccount.addToExisingInverment(investment,am);
        for (Investment in: investments
             ) {
            if(in.equals(investment)){
                in.addToAmount(am);
            }
        }
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
