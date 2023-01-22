package GUI;
import Chart.ModelPieChart;
import Chart.PieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import System.HomeAccount;
import System.User;
import System.UserAdult;
import System.Expense;
import System.CategoryOfExpense;
import System.Income;
import System.Investment;

public class UserPage extends JFrame {
    /*
    Piotrek

        TODO
    wyświetl stan konta - domowy
    private JLabel balance;
        twoje przychody - liczba
    private JLabel personalIncomes;
        ogólne przychody
    private JLabel incomes;
        twoje wydatki
    private JLabel personalExpenses;
        ogólne wydatki
    private JLabel expenses;
        pokaż kwotę zainwestowaną
    private JLabel invested;
        TODO
    wykres do przychodów - ????
    private JButton incomePlot;
        TODO
    wykres wydatków w kategoriach
    private JButton expensePlot;
    wybierz zakres data
    private J
    dodawanie wydatków: - oddzielny page itd., addExpense, addInvestment, addExpense
    oddzielnie wydatki itd. dla homeaccount i userów - zapisywanie do plików
    TODO
    dodawanie Usera i childUsera - checkbox
    TODO
    przy dodawaniu wydatków - kategoria kieszonkowe dla dziecka, updatuje też stan przychodów dziecka
    TODO
    wyświetl listę przychodów i wydatków - 10 pierwszych
    */
    public static final int W_FRAME = 720;
    public static final int H_FRAME = 360;
    private JPanel contentPane;
    //    private JLabel balanceLabel, personalBalanceLabel;
    private JTextField balance;
    private Insets insets;
    private User user;
    private LocalDate start = LocalDate.now().minusMonths(1);
    private LocalDate end = LocalDate.now().plusDays(1);
    private JPanel panel1;
    private JTable table;
    private int startMonthInt = 1;
    private int startYearInt = 2018;
    private int endMonthInt = 12;
    private int endYearInt = 2023;
    private boolean wholeHouseSelected = false;
    private final int h=20;
    private String[][] data = new String[10][2];
    private boolean firstTable = true;

    public UserPage(User u) {
        super("UserPage");
        user = u;
        setResizable(false);

        setSize(W_FRAME, H_FRAME);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        UserPageGUI();

    }

    private void UserPageGUI() {
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setSize(W_FRAME, H_FRAME);

        JLabel banner = new JLabel();
        banner.setText("Welcome " + user.getFirstName() + " " + user.getSurname() + "!");
        banner.setFont(new Font("Comic sans", Font.BOLD, 24));
        banner.setBounds(5, 5, 300, 30);
        contentPane.add(banner);

        JButton refresh = new JButton();
        refresh.setText("Refresh");
        refresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
        refresh.setBounds(580, 5, 120, 25);
        refresh.setFocusable(false);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == refresh) {
                    UserPageGUI();
                }
            }
        });

        contentPane.add(refresh);



//        String personalBalanceString = "Your balance: " + user.getPersonalBalanceDateRange(start, end) + "pln";
        String personalBalanceString = "Your balance: " + Math.round( user.getPersonalBalanceDateRange(start, end) * 100) / 100d + "pln";

        String balanceString = "Balance of the home: " +
                Math.round(100 * user.getHomeAccount().getBalanceDateRange(start, end)) / 100d + "pln";
        String investmentString = "Amount invested: " +
                Math.round(100*user.getAmountInvested() ) / 100d + "pln";
//                Math.round(100*user.getHomeAccount().investedAmount() ) / 100d + "pln";

        JLabel personalBalanceLabel = new JLabel();
        JLabel balanceLabel = new JLabel();
        JLabel investmentLabel = new JLabel();

        investmentLabel.setText(investmentString);
        balanceLabel.setText(balanceString);
        personalBalanceLabel.setText(personalBalanceString);

        balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        personalBalanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        investmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        balanceLabel.setBounds(0,20 + h,300,20);
        personalBalanceLabel.setBounds(0,40 + h,300,20);
        investmentLabel.setBounds(0, 60 + h, 300, 20);

        contentPane.add(balanceLabel);
        contentPane.add(personalBalanceLabel);
        contentPane.add(investmentLabel);



        // Column Names
        String[] columnNames = { "Incomes", "Expenses"}; //tu cos nie działa
        JLabel tableTitle1 = new JLabel();
        tableTitle1.setText("Incomes:");
        tableTitle1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        tableTitle1.setBounds(310,20 + h,100,20);
        contentPane.add(tableTitle1);

        JLabel tableTitle2 = new JLabel();
        tableTitle2.setText("Expenses:");
        tableTitle2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        tableTitle2.setBounds(510,20 + h,100,20);
        contentPane.add(tableTitle2);


        updateTable();

        String months[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String years[] = {"2018","2019","2020","2021","2022","2023"};

        JComboBox startMonth = new JComboBox(months);
        JComboBox startYear = new JComboBox(years);

        JComboBox endMonth = new JComboBox(months);
        JComboBox endYear = new JComboBox(years);

        startMonth.setFocusable(false);
        startYear.setFocusable(false);
        endMonth.setFocusable(false);
        endYear.setFocusable(false);

        startMonth.setBounds(5,100 + h,40,20);
        startYear.setBounds(45,100 + h,55,20);

        endMonth.setBounds(145,100 + h,40,20);
        endYear.setBounds(185,100 + h,55,20);

        startMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == startMonth) {
                    startMonthInt = startMonth.getSelectedIndex() + 1;
                    String monthString = startMonthInt > 10 ? Integer.toString(startMonthInt) : "0" + startMonthInt;
                    String s = "01/" + monthString + "/" + startYearInt;
//                    System.out.println(s);
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        start = LocalDate.parse(s, formatter);
//                        System.out.println(start);
                    }
                    catch(DateTimeException dte){
                        System.out.println("Wrong date format");
                    }
//                    UserPageGUI();
                }
            }
        });

        startYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == startYear) {
//                    startMonthInt = startMonth.getSelectedIndex() + 1;
                    startYearInt = startYear.getSelectedIndex() + 2018;
                    String monthString = startMonthInt > 10 ? Integer.toString(startMonthInt) : "0" + startMonthInt;
                    String s = "01/" + monthString + "/" + startYearInt;
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        start = LocalDate.parse(s, formatter);
                    }
                    catch(DateTimeException dte){
                        System.out.println("Wrong date format");
                    }
//                    UserPageGUI();
                }
            }
        });

        endMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == endMonth) {
                    endMonthInt = endMonth.getSelectedIndex() + 1;
                    String monthString = endMonthInt > 10 ? Integer.toString(endMonthInt) : "0" + endMonthInt;
                    String endDay;
                    if(endMonthInt == 2) {
                        endDay = Integer.toString(29 - Math.min(endYearInt % 4, 1));
                    } else if(endMonthInt <= 7) {
                        endDay = Integer.toString(30 + endMonthInt % 2);
                    } else {
                        endDay = Integer.toString(31 - endMonthInt % 2);
                    }

                    String s = endDay + "/" + monthString + "/" + endYearInt;
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        end = LocalDate.parse(s, formatter);
                    }
                    catch(DateTimeException dte){
                        System.out.println("Wrong date format");
                    }
//                    UserPageGUI();
                }
            }
        });

        endYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == endYear) {
                    endYearInt = endYear.getSelectedIndex() + 2018;
                    String monthString = endMonthInt > 10 ? Integer.toString(endMonthInt) : "0" + endMonthInt;
                    String endDay;
                    if(endMonthInt == 2) {
                        endDay = Integer.toString(29 - Math.min(endYearInt % 4, 1));
                    } else if(endMonthInt <= 7) {
                        endDay = Integer.toString(30 + endMonthInt % 2);
                    } else {
                        endDay = Integer.toString(31 - endMonthInt % 2);
                    }

                    String s = endDay + "/" + monthString + "/" + endYearInt;
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        end = LocalDate.parse(s, formatter);
                    }
                    catch(DateTimeException dte){
                        System.out.println("Wrong date format");
                    }
//                    UserPageGUI();
                }
            }
        });

        contentPane.add(startMonth);
        contentPane.add(startYear);
        contentPane.add(endMonth);
        contentPane.add(endYear);

        JLabel startDateLabel = new JLabel();
        JLabel endDateLabel = new JLabel();

        startDateLabel.setText("Choose start date:");
        endDateLabel.setText("Choose end date:");

        startDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        endDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        startDateLabel.setBounds(0,80 + h,140,20);
        endDateLabel.setBounds(140,80 + h,140,20);

        contentPane.add(startDateLabel);
        contentPane.add(endDateLabel);

        JButton mtd = new JButton();
        mtd.setText("Month to date");
        mtd.setFocusable(false);
        mtd.setBounds(65, 125 + h, 120,20);

        mtd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == mtd) {
                    end = LocalDate.now().plusDays(1);
                    start = LocalDate.of(end.getYear(), end.getMonthValue(), 1);
//                    UserPageGUI();
                }
            }
        });

        contentPane.add(mtd);

//        ImageIcon imageIcon = new ImageIcon("pie4.png");


        JButton pieChart = new JButton();
        pieChart.setBounds(40, 150 + h, 170,30);
        pieChart.setText("Display plot");
        pieChart.setFont(new Font("Tahoma", Font.BOLD, 18));
        pieChart.setFocusable(false);

        pieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == pieChart) {
//                    if (wholeHouseSelected) {
                        JFrame frame = new JFrame("Plot");
                        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        frame.setSize(420, 420);
                        List<ModelPieChart> data = plotData(user);

                        PieChart plot = new PieChart();
                        for (var d : data) {
                            plot.addData(d);
                        }
                        frame.add(plot);
                        frame.setVisible(true);
//                        UserPageGUI();
//                    } else {
//                        JFrame frame = new JFrame("Plot");
//                        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//                        frame.setSize(420, 420);
//                        List<ModelPieChart> data = plotData(user);
//
//                        PieChart plot = new PieChart();
//                        for (var d : data) {
//                            plot.addData(d);
//                        }
//                        frame.add(plot);
//                        frame.setVisible(true);
//                    }
                }
            }
        });

        contentPane.add(pieChart);

        JCheckBox typeOfPlot = new JCheckBox();
        typeOfPlot.setText("Whole house");
        typeOfPlot.setBounds(210, 155 + h, 100, 20);

        typeOfPlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == typeOfPlot) {
                    wholeHouseSelected = typeOfPlot.isSelected();
//                    UserPageGUI();
                }
            }
        });

        contentPane.add(typeOfPlot);

        JButton extendedHistory = new JButton();
        extendedHistory.setBounds(20, 185 + h, 210, 30);
        extendedHistory.setText("Extended history");
        extendedHistory.setFont(new Font("Tahoma", Font.BOLD, 17));
        extendedHistory.setFocusable(false);

        extendedHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == extendedHistory) {
                    LongHistory frame = new LongHistory(user);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//                    UserPageGUI();
                }
            }
        });
        contentPane.add(extendedHistory);

        JButton addExpense = new JButton();
        JButton addIncome = new JButton();
        JButton addInvestment = new JButton();
        JButton removeInvestment = new JButton();

        // 720 x 360

//        addExpense.setBounds(52, 240 + h, 170, 30);
//        addExpense.setText("Add expense");
//        addExpense.setFont(new Font("Tahoma", Font.BOLD, 17));
//        addExpense.setFocusable(false);
//
//        addIncome.setBounds(52+53+170, 240 + h, 170, 30);
//        addIncome.setText("Add income");
//        addIncome.setFont(new Font("Tahoma", Font.BOLD, 17));
//        addIncome.setFocusable(false);
//
//        addInvestment.setBounds(52+53+170+170+53, 240 + h, 170, 30);
//        addInvestment.setText("Add investment");
//        addInvestment.setFont(new Font("Tahoma", Font.BOLD, 17));
//        addInvestment.setFocusable(false);
//
//        removeInvestment.setBounds(52+53+170+170+53, 240 + h, 170, 30);
//        removeInvestment.setText("Remove investment");
//        removeInvestment.setFont(new Font("Tahoma", Font.BOLD, 17));
//        removeInvestment.setFocusable(false);

        addExpense.setBounds(8-4, 240 + h, 170, 30);
        addExpense.setText("Add expense");
        addExpense.setFont(new Font("Tahoma", Font.BOLD, 15));
        addExpense.setFocusable(false);

        addIncome.setBounds(8+8+170-4, 240 + h, 170, 30);
        addIncome.setText("Add income");
        addIncome.setFont(new Font("Tahoma", Font.BOLD, 15));
        addIncome.setFocusable(false);

        addInvestment.setBounds(8+8+170+8+170-4, 240 + h, 170, 30);
        addInvestment.setText("Add investment");
        addInvestment.setFont(new Font("Tahoma", Font.BOLD, 15));
        addInvestment.setFocusable(false);

        removeInvestment.setBounds(8+8+170+8+170+170+8-4, 240 + h, 170, 30);
        removeInvestment.setText("Sell investment");
        removeInvestment.setFont(new Font("Tahoma", Font.BOLD, 15));
        removeInvestment.setFocusable(false);



        addIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addIncome) {
                    AddIncoming frame = new AddIncoming(user);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    updateTable();
//                    UserPageGUI();
                }
            }
        });
        addExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addExpense) {
                    AddExpense frame = new AddExpense(user);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    updateTable();
//                    table = new JTable(data, columnNames);
//                    UserPageGUI();
                }
            }
        });
        addInvestment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addInvestment) {
                    AddInvestment frame = new AddInvestment(user);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    updateTable();
//                    UserPageGUI();
                }
            }
        });
        removeInvestment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == removeInvestment) {
                    RemoveInvestment frame = new RemoveInvestment(user);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
            }
        });

        contentPane.add(addIncome);
        contentPane.add(addExpense);
        contentPane.add(addInvestment);
        contentPane.add(removeInvestment);

        JButton addUser = new JButton();
        addUser.setText("Add user");
        addUser.setBounds(8+8+170+8+170+170+8-4+25, 275 + h, 170-50, 20);
        addUser.setFont(new Font("Tahoma", Font.BOLD, 14));
        addUser.setFocusable(false);

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addUser) {
                    AddUser frame = new AddUser(user.getHomeAccount());
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
            }
        });

        contentPane.add(addUser);

        setContentPane(contentPane);
        setVisible(true);
    }

    private List<ModelPieChart> plotData(User user) {
        ArrayList<ModelPieChart> list = new ArrayList<>();
        List<Expense> expenses = !wholeHouseSelected ? user.getExpenses() : user.getHomeAccount().getExpenses();

        double[] amounts = new double[CategoryOfExpense.values().length];

        for(var e : expenses) {
            if((e.getDate().isBefore(end) && e.getDate().isAfter(start)) || e.getDate().isEqual(start) || e.getDate().isEqual(end))
                amounts[e.getCategoryOfExpense().getId()] -= e.getAmount();
        }

        for(int i=0;i<amounts.length;i++) {
            if (amounts[i] != 0)
                list.add(new ModelPieChart(CategoryOfExpense.values()[i].name(),
                        amounts[i], CategoryOfExpense.values()[i].getColor()));
        }

        return list;
    }

    private void updateTable() {
//        if (firstTable)
//            contentPane.remove(table);
//        firstTable = false;
        String[] columnNames = {"Incomes", "Expenses"};

        String[] expenses = new String[10];
        String[] incomes = new String[10];
//        Object[] exs = user.getExpenses().toArray();

        for(int i=0; i<10;i++) {
            incomes[i] = "";
            expenses[i] = "";
        }

        int counter = 0;
        for(var expense : user.getExpenses()) {
//            if(expense.getDate().isAfter(start) && expense.getDate().isBefore(end)) {
                expenses[counter] = expense.toString();
                counter ++;
                if(counter > 9)
                    break;
//            }
        }
        counter = 0;
        for(var income : user.getIncomings()) {
//            if(income.getDate().isAfter(start) && income.getDate().isBefore(end)) {
                incomes[counter] = income.toString();
                counter ++;
                if(counter > 9)
                    break;
//            }

        }



        for(int i=0;i<10;i++) {
            data[i][0] = incomes[i];
            data[i][1] = expenses[i];
        }

        table =  new JTable(data, columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        table.setBounds(310,55 + h, 400,160);
        contentPane.add(table);
        contentPane.remove(table);
        contentPane.add(table);
    }

    public static void main(String[] args) {
//        UserAdult user = new UserAdult(new HomeAccount(), "sss","swsaw","p","k");
//        user.addExpense(new Expense(21, CategoryOfExpense.GROCERIES, false));
        UserAdult user = new UserAdult(new HomeAccount(),"ws","wae","swww","ws");
        user.addIncoming(new Income(42.3));
        user.addIncoming(new Income(32.3));
        user.addIncoming(new Income(12.3));
        user.addIncoming(new Income(42.3));
        user.addExpense(new Expense(21.1,CategoryOfExpense.GROCERIES, false));
        user.addInvestment(new Investment(420, "wfwf"));


        new UserPage(user);
//        frame.setVisible(true);
    }


}
