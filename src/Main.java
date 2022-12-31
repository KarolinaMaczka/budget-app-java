import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dziala");
        HomeAccount ha= new HomeAccount();
        UserAdult u = new UserAdult(ha,"sda","sfa");
        LocalDate date = LocalDate.of(2023, 1, 8);
        Expense e = new Expense(456,CategoryOfExpense.GROCERIES,false);

        u.addExpense(e);
        double a = u.getPersonalBalance(date);
        System.out.println(a);
    }

}