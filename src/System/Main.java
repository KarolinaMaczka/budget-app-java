package System;

import GUI.AddInvestment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Dziala");
//        HomeAccount ha= new HomeAccount();
//        UserAdult u = new UserAdult(ha,"sda","sfa","nem","smkd");
//        System.out.println(ha.getUsers());
//        System.out.println(ha.containsLogin("sfa"));
//
//        LocalDate date = LocalDate.of(2023, 1, 8);
//        Expense e = new Expense(456,CategoryOfExpense.GROCERIES,false);
//
//        u.addExpense(e);
//        double pb = u.getPersonalBalance(date);
//        System.out.println(pb);
//        double pbdr = u.getPersonalBalanceDateRange(LocalDate.of(2020, 1, 8),LocalDate.of(2023, 1, 8));
//        System.out.println(pbdr);
//        System.out.println(ha.getClass());
//
//        u.addInvestment(new Investment(32, "mieszkanie"));
//        u.addInvestment(new Investment(12124, "rower"));
//        System.out.println(u.investments.isEmpty());
//        if(u.investments.isEmpty()){
//            System.out.println("jest pusta");
//        }
//
//        System.out.println("Inwestycje:" + u.getInvestments());
//        System.out.println(u.getAmountInvested());
//        List<Investment> investmentArray;
//        investmentArray = u.getInvestments();
//        System.out.println(investmentArray);
//        List<String> nazwy = new ArrayList<>();
//        for(Investment i: investmentArray){
//            nazwy.add(i.getName());
//            System.out.println(i.getName() + " kwota: "+ i.getAmount());
//
//        }
//        System.out.println("Program");
//
//        //String[] nazwy2 = nazwy.toArray(String[]::new);
//        //System.out.println(nazwy2[0]);
//        if(!u.getInvestments().isEmpty()){
//            System.out.println("popmoccww");
//        }
//        for(Investment i: u.getInvestments()){
//            nazwy.add(i.getName());
//            System.out.println(i.getName() + " kwota: "+ i.getAmount());
//
//        }
//        System.out.println(u.getAmountInvested());
        RecurringExpense rec = new RecurringExpense(12,CategoryOfExpense.FUEL,FrequencyOfExpsense.MONTHLY,3);
        System.out.println(rec);
        HomeAccount ha= new HomeAccount();
        UserAdult u = new UserAdult(ha,"sda","sfa","nem","smkd");
        u.addRecurringExpense(rec);
        System.out.println(u.getExpenses());

    }


}