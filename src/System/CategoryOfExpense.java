package System;

import java.io.Serializable;

public enum CategoryOfExpense implements Serializable {
    GROCERIES(0),
    HYGIENE(1),
    FUEL(2),
    OTHER(3);


    int id;

    CategoryOfExpense(int id) {
        this.id = id;
    }
}
