package System;

import java.awt.*;

public enum CategoryOfExpense {
    GROCERIES(0, Color.orange),
    HYGIENE(1, Color.cyan),
    FUEL(2, Color.red),
    OTHER(3, Color.magenta),
    POCKET_MONEY(4, Color.green);


    int id;
    Color color;

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    CategoryOfExpense(int id, Color color) {
        this.id = id;
        this.color = color;
    }
}
