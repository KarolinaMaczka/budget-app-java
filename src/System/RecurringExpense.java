package System;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurringExpense {
//    private class
//    protected FrequencyOfExpsense frequency;
//    protected int numberOfTimes;
    private ArrayList<LocalDate> datesOfPayments;
    private double amount;
    private CategoryOfExpense category;

    public RecurringExpense(double amount, CategoryOfExpense category, FrequencyOfExpsense frequency, int numberOfTimes) {
//        super(amount, category);
//        this.id = "R" + this.id;
        this.amount = amount;
        this.category = category;
        this.datesOfPayments = new ArrayList<>();
        this.datesOfPayments.add(0, LocalDate.now());

        switch (frequency.id) {
            case 0:
                for(int i=1; i<numberOfTimes;i++) {
                    this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusDays(1));
                }
            case 1:
                for(int i=1; i<numberOfTimes;i++) {
                    this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusWeeks(1));
                }
            case 2:
                for(int i=1; i<numberOfTimes;i++) {
                    this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusMonths(1));
                }
            case 3:
                for(int i=1; i<numberOfTimes;i++) {
                    this.datesOfPayments.add(i, this.datesOfPayments.get(i-1).plusYears(1));
                }
        }
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



}

