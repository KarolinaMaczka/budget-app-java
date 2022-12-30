import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dziala");
    }
    HomeAccount ha= new HomeAccount();
    UserAdult u1 = new UserAdult(ha,"sda","sfa");
    LocalDate date = LocalDate.of(2020, 1, 8);
    Expense e = new Expense(456,CategoryOfExpense.GROCERIES,false);
    u1.addExpense(e);
    u1.getExpenses(LocalDate.now);
}