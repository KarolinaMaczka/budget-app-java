import java.time.LocalDate;
import java.util.ArrayList;

public class RecurringExpense extends Expense {
    protected FrequencyOfExpsense frequency;
//    protected int numberOfTimes;
    protected ArrayList<LocalDate> datesOfPayments;

    public RecurringExpense(double amount, FrequencyOfExpsense frequency, int numberOfTimes) {
        super(amount);
        this.id = "R" + this.id;
        this.datesOfPayments = new ArrayList<>();
        this.datesOfPayments.add(0, this.date);
            switch (frequency.id) {
                case 1:
                    for(int i=1; i<numberOfTimes;i++) {
                        this.datesOfPayments.add(0, this.datesOfPayments.get(i-1).plusDays(1));
                    }
                case 2:
                    for(int i=1; i<numberOfTimes;i++) {
                        this.datesOfPayments.add(0, this.datesOfPayments.get(i-1).plusWeeks(1));
                    }
                case 3:
                    for(int i=1; i<numberOfTimes;i++) {
                        this.datesOfPayments.add(0, this.datesOfPayments.get(i-1).plusMonths(1));
                    }
                case 4:
                    for(int i=1; i<numberOfTimes;i++) {
                        this.datesOfPayments.add(0, this.datesOfPayments.get(i-1).plusYears(1));
                    }
            }
    }


}

