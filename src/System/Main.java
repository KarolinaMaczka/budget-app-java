package System;

import GUI.AddInvestment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dziala");
        HomeAccount ha= new HomeAccount();
        UserAdult u = new UserAdult(ha,"sda","sfa","nem","smkd");
        System.out.println(ha.getUsers());
        System.out.println(ha.containsLogin("sfa"));

        LocalDate date = LocalDate.of(2023, 1, 8);
        Expense e = new Expense(456,CategoryOfExpense.GROCERIES,false);

        u.addExpense(e);
        double pb = u.getPersonalBalance(date);
        System.out.println(pb);
        double pbdr = u.getPersonalBalanceDateRange(LocalDate.of(2020, 1, 8),LocalDate.of(2023, 1, 8));
        System.out.println(pbdr);
        System.out.println(ha.getClass());

        u.addInvestment(new Investment(32, "mieszkanie"));
        u.addInvestment(new Investment(12124, "rower"));
        System.out.println("Inwestycje:" + u.getInvestments());
        List<Investment> investmentArray;
        investmentArray = u.getInvestments();
        System.out.println(investmentArray);
        List<String> nazwy = new ArrayList<>();
        for(Investment i: investmentArray){
            nazwy.add(i.getName());
            System.out.println(i.getName() + " kwota: "+ i.getAmount());

        }
        System.out.println("Program");

        //String[] nazwy2 = nazwy.toArray(String[]::new);
        //System.out.println(nazwy2[0]);
        for(Investment i: investmentArray){
            nazwy.add(i.getName());
            System.out.println(i.getName() + " kwota: "+ i.getAmount());

        }

    }

}