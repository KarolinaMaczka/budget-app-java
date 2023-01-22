package GUI;

import javax.swing.*;
import java.awt.*;
import System.User;

public class RemoveInvestment extends JFrame {
    public static final int W_FRAME = 540;
    public static final int H_FRAME = 360;
    private Insets insets;
    private JPanel contentPane;

    private User user;

    public RemoveInvestment(User ur){
        setTitle("Remove Investment");
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);

        setVisible(true);
        insets = this.getInsets();
        this.user = ur;

        SellInvestmentGUI();
    }
    public void SellInvestmentGUI(){

    }
}
