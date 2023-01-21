package GUI;

import System.HomeAccount;

import javax.swing.*;
import System.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import System.Income;
import System.UserAdult;

public class AddIncoming extends JFrame {
    //magda
    //przy childzie updatujemy listę tylko dla childa // to juz jest metodzie zrobiuoine

    public static final int W_FRAME = 540;
    public static final int H_FRAME = 360;

    private JLabel labelAmount;
    private JTextField textAmount;
    private JPanel contentPane;
    private JButton buttonApprove;
    private Insets insets;

    private HomeAccount homeAccount;
    private User user;

    public AddIncoming(User ur) {
        setTitle("Add incoming");
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        insets = this.getInsets();
        user = ur;

        AddIncomingGUI();
    }

    private void AddIncomingGUI(){
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        labelAmount = new JLabel("Amount");


        labelAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelAmount.setBounds(120,140,70,20);
        contentPane.add(labelAmount);

        textAmount = new JTextField();
        textAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textAmount.setBounds(220,140,120,20);
        contentPane.add(textAmount);

        buttonApprove = new JButton("OK");
        buttonApprove.setFont(new Font("Tahoma", Font.PLAIN, 14));
        buttonApprove.setBounds(220,200,70,20);
        buttonApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = Integer.parseInt(textAmount.getText());
                user.addIncoming(new Income(number));

                }

        });
        contentPane.add(buttonApprove);


        setContentPane(contentPane);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            HomeAccount homeAccount= new HomeAccount();
            User user = new UserAdult(homeAccount, "sda","sfa", "Magdalena", "Jeczen");
            @Override
            public void run() {

                new AddIncoming(user);

            }
        });

    }

}
