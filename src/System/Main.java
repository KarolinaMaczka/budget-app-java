package System;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dziala");
        HomeAccount ha= new HomeAccount();
        UserAdult u = new UserAdult(ha,"sda","sfa");
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

    }

}