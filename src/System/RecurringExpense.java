package System;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurringExpense implements Serializable {
    private ArrayList<LocalDate> datesOfPayments;
    private double amount;
    private CategoryOfExpense category;
    private List<Expense> addToExpenseList;

    public RecurringExpense(double amount, CategoryOfExpense category, FrequencyOfExpsense frequency, int numberOfTimes) {

        this.amount = amount;
        this.category = category;
        this.datesOfPayments = new ArrayList<>();
        this.datesOfPayments.add(0, LocalDate.now());

        if (frequency.equals(FrequencyOfExpsense.DAILY)) {
            for (int i = 1; i < numberOfTimes; i++) {
                this.datesOfPayments.add(i, this.datesOfPayments.get(i - 1).plusDays(1));
            }
        }else if (frequency.equals(FrequencyOfExpsense.WEEKLY)) {
            for(int i=1; i<numberOfTimes;i++) {
                this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusWeeks(1));
            }
        }else if (frequency.equals(FrequencyOfExpsense.MONTHLY)) {
            for(int i=1; i<numberOfTimes;i++) {
                this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusMonths(1));
            }
        }else if (frequency.equals(FrequencyOfExpsense.YEARLY)) {
            for(int i=1; i<numberOfTimes;i++) {
                this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusYears(1));
            }
        }
        this.addToExpenseList=allExpenses();
    }

    public List<Expense> allExpenses(){
        List<Expense> res = new ArrayList<>();
        int counter=0;
        for(var date : this.datesOfPayments) {
            Expense e= new Expense(this.amount, this.category, true);
            e.setDate(date);
            res.add(e);
            counter++;
        }
        for(int i=0;i<counter;i++) this.datesOfPayments.remove(0);
        return res;
    }

    public List<Expense> pastExpenses() {
        List<Expense> res = new ArrayList<>();
        int counter=0;
        for(var date : this.datesOfPayments) {
            if(date.isBefore(LocalDate.now())) {
                res.add(new Expense(this.amount, this.category, true));
                counter++;
            }
        }
        for(int i=0;i<counter;i++) this.datesOfPayments.remove(0);
        return res;
    }

    public List<Expense> getAddToExpenseList() {
        return addToExpenseList;
    }

    @Override
    public String toString() {
        return "RecurringExpense{" +
                "datesOfPayments=" + datesOfPayments +
                ", amount=" + amount +
                ", category=" + category +
                ", expenses=" + addToExpenseList +
                '}';
    }
}

