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

    private int W_FRAME=1000,H_FRAME=600;
    private Insets insets;
//    private JPanel contentPane;
//    private JFormattedTextField startingDateField, endDateField;
//    private LocalDate startingDate = LocalDate.of(2000, 1, 1);
//    private LocalDate endDate = LocalDate.now();
//    private JLabel dateStartLabel, dateEndLabel, expensesLabel, incomingsLabel;
    private User user;
//    private JTable tableExpenses;
    private ContentPane contentPane;

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

        this.user=user;

//        LongHistoryGUI();
        contentPane = new ContentPane();
        setContentPane(contentPane);

    }

    private class ContentPane extends JPanel{
        public ExpensePane  expensePanel;
        public ChoicePane choicePanel;
        public IncomePane incomingsPanel;

        public ContentPane() {
            super();
            this.expensePanel = new ExpensePane(LocalDate.of(2000, 1, 1),LocalDate.now());
            this.incomingsPanel = new IncomePane(LocalDate.of(2000, 1, 1),LocalDate.now());
            this.choicePanel = new ChoicePane(this);

            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(choicePanel)
                                    .addComponent(incomingsPanel)
                            )
                            .addComponent(expensePanel)
            );

            layout.setVerticalGroup(
                    layout.createSequentialGroup()
                            .addComponent(choicePanel)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(incomingsPanel)
                                    .addComponent(expensePanel)
                            )
            );

        }
    }

    private class ChoicePane extends JPanel{
        private JLabel dateStartLabel, dateEndLabel;
        private JFormattedTextField startingDateField, endDateField;

        public ChoicePane(ContentPane contentPane) {
            super();
            dateStartLabel = new JLabel("Choose start date");
            dateEndLabel = new JLabel("Choose end date");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            startingDateField = new JFormattedTextField(df);
            startingDateField.setMaximumSize(new Dimension(80,20));
            startingDateField.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS) || (c== KeyEvent.VK_ENTER) ))
                    {
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                                        "Format: yyyy-MM-dd");
                        e.consume();
                    }
                }
            });
            startingDateField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate startingDate = LocalDate.parse(startingDateField.getText(), formatter);
                        System.out.println(startingDate);
                        contentPane.expensePanel.setStartingDate(startingDate);
                        contentPane.expensePanel.revalidate();
                        contentPane.incomingsPanel.setStartingDate(startingDate);
                        contentPane.incomingsPanel.revalidate();
                    }catch(DateTimeException dte){
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                                "Format: yyyy-MM-dd");
                    }
                }
            });

            endDateField = new JFormattedTextField(df);
            endDateField.setMaximumSize(new Dimension(80,20));
            endDateField.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS) || (c== KeyEvent.VK_ENTER) ))
                    {
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                                "Format: yyyy-MM-dd");
                        e.consume();
                    }
                }
            });
            endDateField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate endDate = LocalDate.parse(endDateField.getText(), formatter);
                        System.out.println(endDate);
                        contentPane.expensePanel.setEndDate(endDate);
                        contentPane.expensePanel.revalidate();
                        contentPane.incomingsPanel.setEndDate(endDate);
                        contentPane.incomingsPanel.revalidate();
                    }
                    catch(DateTimeException dte){
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Date \n" +
                                "Format: yyyy-MM-dd");
                    }
                }
            });

            GroupLayout layoutCP = new GroupLayout(this);
            this.setLayout(layoutCP);
            layoutCP.setAutoCreateGaps(true);
            layoutCP.setAutoCreateContainerGaps(true);

            layoutCP.setHorizontalGroup(
                    layoutCP.createSequentialGroup()
                            .addGroup(layoutCP.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(dateStartLabel)
                                    .addComponent(startingDateField)
                            )
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layoutCP.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(dateEndLabel)
                                    .addComponent(endDateField)
                            )
            );

            layoutCP.setVerticalGroup(
                    layoutCP.createSequentialGroup()
                            .addGroup(layoutCP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(dateStartLabel)
                                    .addComponent(dateEndLabel)
                            )
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layoutCP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(startingDateField)
                                    .addComponent(endDateField)
                            )
            );

        }
    }

    private class ExpensePane extends JPanel{
        private LocalDate startingDate;
        private LocalDate endDate;
        private JLabel expensesLabel;
        private JScrollPane scrollPaneExpenses;
        public ExpensePane(LocalDate startingDate,LocalDate endDate) {
            super();
            this.startingDate=startingDate;
            this.endDate=endDate;
            this.expensesLabel = new JLabel("Your expenses");
            this.scrollPaneExpenses = new JScrollPane();

            this.setLayout(new FlowLayout());
            this.add(this.scrollPaneExpenses);
            this.add(expensesLabel);

            createTable(this.startingDate,this.endDate);

        }

        public void setStartingDate(LocalDate startingDate) {
            this.startingDate = startingDate;
            createTable(this.startingDate,this.endDate);

        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            createTable(this.startingDate,this.endDate);
        }

        private void createTable(LocalDate startingDate,LocalDate endDate){
            this.remove(this.scrollPaneExpenses);

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

            JTable tableExpenses = new JTable(data, columnNames){
                public boolean isCellEditable(int row, int column) {
                    return false;
                };
            };
            this.scrollPaneExpenses = new JScrollPane(tableExpenses);
            tableExpenses.setFillsViewportHeight(true);

            this.add(this.scrollPaneExpenses);
            this.revalidate();

        }
    }

    private class IncomePane extends JPanel{
        private LocalDate startingDate;
        private LocalDate endDate;
        private JLabel incomingsLabel;
        private JScrollPane scrollPaneIncome;
        public IncomePane(LocalDate startingDate,LocalDate endDate) {
            super();
            this.startingDate=startingDate;
            this.endDate=endDate;
            this.incomingsLabel = new JLabel("Your incomings");
            this.scrollPaneIncome = new JScrollPane();

            this.setLayout(new FlowLayout());
            this.add(this.scrollPaneIncome);
            this.add(incomingsLabel);

            createTable(this.startingDate,this.endDate);

        }

        public void setStartingDate(LocalDate startingDate) {
            this.startingDate = startingDate;
            createTable(this.startingDate,this.endDate);
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            createTable(this.startingDate,this.endDate);
        }

        private void createTable(LocalDate startingDate,LocalDate endDate){
            this.remove(this.scrollPaneIncome);

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
                    data1[j][0] = in.getDate();
                    data1[j][1] = in.getAmount();
                    j++;
                }
            }

            String[] columnNames1 = {"Date", "Amount (PLN)"};

            JTable tableIncome = new JTable(data1, columnNames1){
                public boolean isCellEditable(int row, int column) {
                    return false;
                };
            };

            this.scrollPaneIncome = new JScrollPane(tableIncome);
            tableIncome.setFillsViewportHeight(true);

            this.add(this.scrollPaneIncome);
            this.revalidate();

        }
    }

    public static void main(String[] args) {

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
