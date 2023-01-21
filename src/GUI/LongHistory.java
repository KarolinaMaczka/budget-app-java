package GUI;

import javax.swing.*;
import System.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.util.JDatePickerUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LongHistory extends JFrame {
    //karolina
    //wyświetla historię, zaznacza czy to wydatek czy przychód, można wybrać zakres dat

    private int W_FRAME=600,H_FRAME=600;
    private Insets insets;
    private JPanel contentPane;
    private JFormattedTextField startingDateField, endDateField;
    private LocalDate startingDate = LocalDate.of(2000, 1, 1);
    private LocalDate endDate = LocalDate.now();


    public LongHistory (User user){
        super("Your history");
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        insets = this.getInsets();

        LongHistoryGUI(user);

    }

    private void LongHistoryGUI(User user) {
        this.setTitle("Your long history");
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        startingDateField = new JFormattedTextField(df);
        startingDateField.setBounds(100,50,70,20);
        startingDateField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH) || (c== KeyEvent.VK_ENTER) ))
                {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                                    "Format: dd/MM/yyyy");
                    e.consume();
                }
            }
        });
        startingDateField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startingDate = LocalDate.parse(startingDateField.getText(), formatter);
                System.out.println(startingDate);}
                catch(DateTimeException dte){
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                            "Format: dd/MM/yyyy");
                }
            }
        });

        endDateField = new JFormattedTextField(df);
        endDateField.setBounds(100,50,70,20);
        endDateField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH) || (c== KeyEvent.VK_ENTER) ))
                {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                            "Format: dd/MM/yyyy");
                    e.consume();
                }
            }
        });
        endDateField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    endDate = LocalDate.parse(endDateField.getText(), formatter);
                    System.out.println(endDate);}
                catch(DateTimeException dte){
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                            "Format: dd/MM/yyyy");
                }
            }
        });


        ArrayList<Expense> listExpense = new ArrayList<>();

        for(Expense e : user.getExpenses()){
            if (e.getDate().isAfter(startingDate) && e.getDate().isBefore(endDate)){
                listExpense.add(e);
            }
        }

        int i =0;
        Object[][] data=new Object[listExpense.size()][3];
        for (Expense expense: listExpense){
            if (expense.getDate().isAfter(startingDate) && expense.getDate().isBefore(endDate)){
            data[i][0] = expense.getCategoryOfExpense();
            data[i][1] = expense.getDate();
            data[i][2] = expense.getAmount();
            i++;
            }
        }

        String[] columnNames = {"Category", "Date", "Amount (PLN)"};

        JTable tableExpenses = new JTable(data, columnNames);
        JScrollPane scrollPaneExpenses = new JScrollPane(tableExpenses);
        tableExpenses.setFillsViewportHeight(true);

        ArrayList<Income> listIncome = new ArrayList<>();

        for(Income e : user.getIncomings()){
            if (e.getDate().isAfter(startingDate) && e.getDate().isBefore(endDate)){
                listIncome.add(e);
            }
        }

        int j =0;
        Object[][] data1=new Object[listIncome.size()][2];
        for (Income in: listIncome){
            if (in.getDate().isAfter(startingDate) && in.getDate().isBefore(endDate)){
                data1[i][0] = in.getDate();
                data1[i][1] = in.getAmount();
                i++;
            }
        }

        String[] columnNames1 = {"Date", "Amount (PLN)"};

        JTable tableIncome = new JTable(data1, columnNames1);
        JScrollPane scrollPaneIncome = new JScrollPane(tableIncome);
        tableIncome.setFillsViewportHeight(true);

        contentPane.setLayout(new FlowLayout());
        contentPane.add(startingDateField);
        contentPane.add(endDateField);
        contentPane.add(scrollPaneExpenses);
        contentPane.add(scrollPaneIncome);
        setContentPane(contentPane);

    }

    public static void main(String[] args) {
//        File homeAccountFile= new File("C:\\Users\\karim\\IdeaProjects\\kontrola-budzetu\\src\\Data\\HomeAccount");
//        HomeAccount homeAccount;
//        if (homeAccountFile.length()==0){
//        }else {
//            ObjectInputStream in = null;
//            try {
//                in = new ObjectInputStream(new FileInputStream(homeAccountFile));
//            } catch (IOException e) {
//                System.out.println("zła ścieżka");
//            }
//
//            try {
//                homeAccount =(HomeAccount) in.readObject();
//                System.out.println(homeAccount.getUsers());
//                User u1= homeAccount.getUsers().get(0);
//
//                EventQueue.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        new LongHistory(u1);
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        HomeAccount ha= new HomeAccount();
        UserAdult u = new UserAdult(ha,"sda","sfa","nem","smkd");
        System.out.println(ha.getUsers());
        System.out.println(ha.containsLogin("sfa"));

        Expense e = new Expense(456,CategoryOfExpense.GROCERIES,false);
        Expense e1 = new Expense(6,CategoryOfExpense.FUEL,false);
        e1.setDate(LocalDate.of(2003, 1, 4));
        Income i = new Income(222);
        u.addIncoming(i);
        u.addExpense(e);
        u.addExpense(e1);

        EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new LongHistory(u);
                    }
                });
    }

}
