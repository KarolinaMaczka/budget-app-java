package System;

import java.io.Serializable;

public enum FrequencyOfExpsense implements Serializable {
    DAILY(0),
    WEEKLY(1),
    MONTHLY(2),
    YEARLY(3);

    int id;

    FrequencyOfExpsense(int id) {
        this.id = id;
    }
}
