package GUI;

import javax.swing.*;

import System.User;
import System.Investment;
import System.HomeAccount;
import System.UserAdult;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class AddInvestment extends JFrame {
    // To GUI odpowiada za dodawanie do nowych lub tworzenie inwestycji
    public static final int W_FRAME = 540;
    public static final int H_FRAME = 360;

    private JTextField textAmount;
    private JLabel labelAmount;
    private JTextField textName;
    private JLabel labelName;
    private JComboBox comboBoxName;
    private JLabel labelChooseName;
    private JButton buttonApprove;
    //private JCheckBox checkBoxNew;
    //private JCheckBox checkBoxExisting;
    private JComboBox comboBoxNewOrExisting;
    private JLabel labelNewOrExisting;

    private Insets insets;
    private JPanel contentPane;

    private User user;
    private List<Investment> investments;


    public AddInvestment(User ur){
        setTitle("Add investment");
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

        AddInvestmentGUI(); // główna funkcja


    }

    private void AddInvestmentGUI(){
        String[] newOrEx = {"Create new","Add to exisitng"};
        investments = user.getInvestments();
        List<String> nazwy = new ArrayList<>();
        for(Investment i: investments){
            nazwy.add(i.getName());
        }
        String[] nazwy2 = nazwy.toArray(String[]::new);


        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        comboBoxNewOrExisting = new JComboBox(newOrEx);
        comboBoxNewOrExisting.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxNewOrExisting.setBounds(220,50,170,20);
        contentPane.add(comboBoxNewOrExisting);

        comboBoxName = new JComboBox(nazwy2);
        comboBoxName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxName.setBounds(220,100,170,20);

        labelChooseName = new JLabel("Choose investment:");
        labelChooseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelChooseName.setBounds(80,100,170,20);


        comboBoxNewOrExisting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((JComboBox)e.getSource()).getSelectedItem() == "Create new"){
                    contentPane.add(labelName);
                    contentPane.add(textName);
                    contentPane.remove(comboBoxName);
                    contentPane.remove(labelChooseName);

                }
                else if(((JComboBox)e.getSource()).getSelectedItem() == "Add to exisitng"){
                    contentPane.add(comboBoxName);
                    contentPane.add(labelChooseName);
                    contentPane.remove(labelName);
                    contentPane.remove(textName);
                }
                contentPane.updateUI();
            }
        });

        labelName = new JLabel("Name:");
        labelName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelName.setBounds(120,100,370,20);

        textName = new JTextField();
        textName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textName.setBounds(220,100,120,20);

        contentPane.add(labelName);
        contentPane.add(textName);

        labelAmount = new JLabel("Amount");
        labelAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelAmount.setBounds(120,160,70,20);
        contentPane.add(labelAmount);


        textAmount= new JTextField();
        textAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textAmount.setBounds(220,160,120,20);
        contentPane.add(textAmount);

        buttonApprove = new JButton("OK");
        buttonApprove.setFont(new Font("Tahoma", Font.PLAIN, 14));
        buttonApprove.setBounds(220,230,70,20);
        buttonApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amountToAdd =0;
                try{
                    amountToAdd = Double.parseDouble(textAmount.getText());
                    if (amountToAdd <= 0) {
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Amount");
                    } else {
                        boolean ok =false;
                        if (comboBoxNewOrExisting.getSelectedItem() == "Create new") {
                            if (textName.getText().equals("")){
                                JOptionPane.showMessageDialog(null, "Please Enter Valid Name");
                            }else{
                                user.addInvestment(new Investment(amountToAdd, textName.getText()));
                                ok=true;
                            }
                        } else {
                            for (String s : nazwy2) {
                                if (comboBoxName.getSelectedItem() == s) {
                                    for (Investment investment : user.getInvestments()) {
                                        if (investment.getName() == s) {
                                            user.addToExisingInvestment(investment, amountToAdd);
                                        }
                                        break;
                                    }
                                }
                            }
                            ok=true;
                        }
                        System.out.println(user.getAmountInvested());
                        System.out.println(user.getInvestments());
                        if (ok){
                            for (Investment i : user.getInvestments()) {
                                nazwy.add(i.getName());
                                System.out.println(i.getName() + " kwota: " + i.getAmount());

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
                                    AddInvestment.this.dispose();
                                }
                            });
                        }
                    }
                }
                catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Amount");
                }

            }
        });

        contentPane.add(buttonApprove);

        setContentPane(contentPane);

    }



}
