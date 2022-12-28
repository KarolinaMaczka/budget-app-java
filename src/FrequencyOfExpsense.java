public enum FrequencyOfExpsense {
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3),
    YEARLY(4);

    int id;

    FrequencyOfExpsense(int id) {
        this.id = id;
    }
}
