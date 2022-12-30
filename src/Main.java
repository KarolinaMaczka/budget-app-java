import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Dziala");
    }
    HomeAccount ha= new HomeAccount();
    UserAdult u1 = new UserAdult(ha,"sda","sfa");
    LocalDate date = LocalDate.of(2020, 1, 8);
    Expense e = new Expense(456,CategoryOfExpense.GROCERIES,date)
    u1.addExpense(e);

}