package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import System.User;
import System.Investment;
import System.UserAdult;
import System.HomeAccount;

public class RemoveInvestment extends JFrame {
    public static final int W_FRAME = 540;
    public static final int H_FRAME = 360;
    private Insets insets;
    private JPanel contentPane;
    private List<Investment> investments;
    private User user;
    private JComboBox comboBoxName;
    private JLabel labelChooseName;

    private JButton buttonDelete;

    public RemoveInvestment(User ur){
        setTitle("Remove Investment");
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);
        investments = new ArrayList<>();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        insets = this.getInsets();
        this.user = ur;

        SellInvestmentGUI();
    }
    public void SellInvestmentGUI(){
        investments = user.getInvestments();
        List<String> nazwy = new ArrayList<>();
        for(Investment i: investments){
            nazwy.add(i.getName());
            System.out.println(i.getName());
        }
        String[] nazwy2 = nazwy.toArray(String[]::new);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        comboBoxName = new JComboBox(nazwy2);
        comboBoxName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxName.setBounds(220,100,170,20);
        contentPane.add(comboBoxName);

        labelChooseName = new JLabel("Choose investment:");
        labelChooseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelChooseName.setBounds(80,100,170,20);
        contentPane.add(labelChooseName);

        buttonDelete= new JButton("Remove");
        buttonDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        buttonDelete.setBounds(220,230,70,20);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user instanceof UserAdult){
                    if(e.getSource() == buttonDelete){
                        for(String s: nazwy2){
                            if(comboBoxName.getSelectedItem() == s){
                                for(Investment investment: investments){
                                    if(investment.getName() == s){
                                        ((UserAdult) user).removeInvestment(investment);
                                        System.out.println(user.getInvestments());
                                        break;
                                    }
                                }
                            }
                        }
                        ObjectOutputStream o = null;
                        try {
                            o = new ObjectOutputStream(new FileOutputStream(".\\src\\Data\\HomeAccount"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            o.writeObject(user.getHomeAccount());
                            ;
                            o.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                RemoveInvestment.this.dispose();
                            }
                        });
                    }
                }

            }
        });
        contentPane.add(buttonDelete);

        setContentPane(contentPane);
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            HomeAccount homeAccount= new HomeAccount();
//            User user = new UserAdult(homeAccount, "sda","sfa", "Magdalena", "Jeczen");
//
//            @Override
//            public void run() {
//                user.addInvestment(new Investment(32, "mieszkanie"));
//                user.addInvestment(new Investment(12124, "rower"));
//                new RemoveInvestment(user);
//            }
//        });
//
//    }
}
