package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import System.HomeAccount;
import System.User;
import System.CategoryOfExpense;
import System.Expense;
import System.UserAdult;
import System.FrequencyOfExpsense;
import System.RecurringExpense;

public class AddExpense extends JFrame {
    //magda

    //przy childzie updatujemy listę tylko dla childa

    public static final int W_FRAME = 540;
    public static final int H_FRAME = 360;

    private JLabel labelAmount;
    private JLabel labelCategory;

    private JLabel labelFrequency;

    private JLabel labelNumberOfTimes;
    private JTextField textAmount;

    private JCheckBox checkBoxReccurence;

    private JComboBox boxExpenseCategory;
    private JPanel contentPane;
    private JButton buttonApprove;
    private Insets insets;

    private JTextField textAmountOfTime;
    private JComboBox comboBoxFrequency;


    private HomeAccount homeAccount;
    private User user;
    public AddExpense(User ur) {
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

        AddExpenseGUI();
    }

    private void AddExpenseGUI(){
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        checkBoxReccurence = new JCheckBox("Reccuring", false);
        checkBoxReccurence.setFont(new Font("Tahoma", Font.PLAIN, 14));
        checkBoxReccurence.setBounds(220,50,170,20);
        contentPane.add(checkBoxReccurence);

        checkBoxReccurence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == checkBoxReccurence){
                    if(((JCheckBox)e.getSource()).isSelected()){
                        contentPane.add(labelFrequency);
                        contentPane.add(comboBoxFrequency);
                        contentPane.add(labelNumberOfTimes);
                        contentPane.add(textAmountOfTime);
                    }
                    else{
                        contentPane.remove(labelFrequency);
                        contentPane.remove(comboBoxFrequency);
                        contentPane.remove(labelNumberOfTimes);
                        contentPane.remove(textAmountOfTime);
                    }
                    contentPane.updateUI();

                }
            }
        });

        labelNumberOfTimes = new JLabel("Times:");
        labelNumberOfTimes.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelNumberOfTimes.setBounds(120,135,120,20);

        textAmountOfTime= new JTextField();
        textAmountOfTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textAmountOfTime.setBounds(220,135,120,20);

        comboBoxFrequency = new JComboBox(FrequencyOfExpsense.values());
        comboBoxFrequency.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxFrequency.setBounds(220,100,120,20);

        labelFrequency = new JLabel("Frequency:");
        labelFrequency.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelFrequency.setBounds(120,100,120,20);

        labelCategory = new JLabel("Category:");
        labelCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelCategory.setBounds(120,170,120,20);
        contentPane.add(labelCategory);

        boxExpenseCategory = new JComboBox(CategoryOfExpense.values());
        boxExpenseCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
        boxExpenseCategory.setBounds(220,170,120,20);
        contentPane.add(boxExpenseCategory);



        labelAmount = new JLabel("Amount");
        labelAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelAmount.setBounds(120,200,70,20);
        contentPane.add(labelAmount);



        textAmount= new JTextField();
        textAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textAmount.setBounds(220,200,120,20);
        contentPane.add(textAmount);





        buttonApprove = new JButton("OK");
        buttonApprove.setFont(new Font("Tahoma", Font.PLAIN, 14));
        buttonApprove.setBounds(220,230,70,20);
        buttonApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double number = Double.parseDouble(textAmount.getText());
                CategoryOfExpense cat = (CategoryOfExpense)boxExpenseCategory.getSelectedItem();
                if(checkBoxReccurence.isSelected() && user instanceof UserAdult){
                    int numberOfTimes = Integer.parseInt(textAmountOfTime.getText());
                    RecurringExpense recurringExpense = new RecurringExpense(number, cat,(FrequencyOfExpsense)comboBoxFrequency.getSelectedItem(),numberOfTimes);
                    user.addRecurringExpense(recurringExpense);
                }
                else{
                    Expense ex = new Expense(number, cat, false);
                    user.addExpense(ex);
                    }
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

                new AddExpense(user);

            }
        });

    }

}
