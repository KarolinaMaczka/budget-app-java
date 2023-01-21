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
    public static final int H_FRAME = 540;
    private JPanel contentPane;
    //    private JLabel balanceLabel, personalBalanceLabel;
    private JTextField balance;
    private Insets insets;
    private User user;
    private LocalDate start;
    private LocalDate end;
    private JPanel panel1;
    private JTable table;
    private int startMonthInt = 1;
    private int startYearInt = 2018;
    private int endMonthInt = 12;
    private int endYearInt = 2023;

    public UserPage(User u) {
        super("UserPage");
        user = u;
        setResizable(false);
//        setResizable(false);
//        setLayout(null);
        setSize(W_FRAME, H_FRAME);
//        setLocationRelativeTo(null);
//        setLocation(getX() - 80, getY() - 80);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setSize(W_FRAME, H_FRAME);

        String personalBalanceString = "Your balance: " + user.getPersonalBalanceDateRange(start, end) + "pln";
        String balanceString = "Balance of the home: " + user.getHomeAccount().getBalanceDateRange(start, end) + "pln";
        String investmentString = "Amount invested: " + user.getAmountInvestedDateRange(start, end) + "pln";

        JLabel personalBalanceLabel = new JLabel();
        JLabel balanceLabel = new JLabel();
        JLabel investmentLabel = new JLabel();

        investmentLabel.setText(investmentString);
        balanceLabel.setText(balanceString);
        personalBalanceLabel.setText(personalBalanceString);

        balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        personalBalanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        investmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        balanceLabel.setBounds(0,20,300,20);
        personalBalanceLabel.setBounds(0,40,300,20);
        investmentLabel.setBounds(0, 60, 300, 20);

        contentPane.add(balanceLabel);
        contentPane.add(personalBalanceLabel);
        contentPane.add(investmentLabel);

        String[][] data = new String[10][2];
        String[] expenses = new String[10];
        String[] incomes = new String[10];

        int i=0;
        for(var e : user.getExpenses()) {
            if(i > 9)
                break;
            expenses[i] = e.toString();
            i++;
        }
        i=0;
        for(var in : user.getIncomings()) {
            if(i > 9)
                break;
            incomes[i] = in.toString();
            i++;
        }

        for(i=0;i<10;i++) {
            data[i][0] = incomes[i];
            data[i][1] = expenses[i];
        }

        // Column Names
        String[] columnNames = { "Incomes", "Expenses"}; //tu cos nie działa
        JLabel tableTitle1 = new JLabel();
        tableTitle1.setText("Incomes");
        tableTitle1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tableTitle1.setBounds(310,20,100,20);
        contentPane.add(tableTitle1);

        JLabel tableTitle2 = new JLabel();
        tableTitle2.setText("Expenses");
        tableTitle2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tableTitle2.setBounds(510,20,100,20);
        contentPane.add(tableTitle2);

        // Initializing the JTable
        table = new JTable(data, columnNames);

        table.setBounds(310,45, 400,160);
        contentPane.add(table);

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

        startMonth.setBounds(5,100,40,20);
        startYear.setBounds(45,100,55,20);

        endMonth.setBounds(145,100,40,20);
        endYear.setBounds(185,100,55,20);

        startMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == startMonth) {
                    startMonthInt = startMonth.getSelectedIndex() + 1;
                    String monthString = startMonthInt > 10 ? Integer.toString(startMonthInt) : "0" + startMonthInt;
                    String s = "01/" + monthString + "/" + startYearInt;
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        start = LocalDate.parse(s, formatter);
                    }
                    catch(DateTimeException dte){
                        System.out.println("Wrong date format");
                    }
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
        startDateLabel.setBounds(0,80,140,20);
        endDateLabel.setBounds(140,80,140,20);

        contentPane.add(startDateLabel);
        contentPane.add(endDateLabel);

        JButton mtd = new JButton();
        mtd.setText("Month to date");
        mtd.setFocusable(false);
        mtd.setBounds(65, 125, 120,20);

        mtd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == mtd) {
                    end = LocalDate.now();
                    start = LocalDate.of(end.getYear(), end.getMonthValue(), 1);
                }
            }
        });

        contentPane.add(mtd);

//        ImageIcon imageIcon = new ImageIcon("pie4.png");


        JButton pieChart = new JButton();
        pieChart.setBounds(40, 150, 170,30);
        pieChart.setText("Display plot");
        pieChart.setFont(new Font("Tahoma", Font.BOLD, 18));
        pieChart.setFocusable(false);

        pieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == pieChart) {
                    JFrame frame = new JFrame("Plot");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(420, 420);
                    List<ModelPieChart> data = plotData(user);

                    PieChart plot = new PieChart();
                    for(var d : data) {
                        plot.addData(d);
                    }
//                    plot.addData( new ModelPieChart("x", 21, Color.blue));
                    frame.add(plot);
                    frame.setVisible(true);
                }
            }
        });

        contentPane.add(pieChart);

        JButton extendedHistory = new JButton();
        extendedHistory.setBounds(20, 185, 210, 30);
        extendedHistory.setText("Extended history");
        extendedHistory.setFont(new Font("Tahoma", Font.BOLD, 17));
        extendedHistory.setFocusable(false);

        extendedHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == extendedHistory) {
                    LongHistory longHistory = new LongHistory(user);
                    longHistory.setVisible(true);
                }
            }
        });
        contentPane.add(extendedHistory);

        setContentPane(contentPane);
        setVisible(true);

    }

//    private void UserPageGUI() {
////        contentPane = new JPanel();
////        contentPane.setLayout(null);
////        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
////                H_FRAME - insets.bottom - insets.top);
////        double personalBalance = user.getPersonalBalanceDateRange(start, end);
//        Double personalBalance = 420.0;
//
////        double homeBalance = user.getPersonalBalanceDateRange(start, end);
//        Double homeBalance = 21.37;
//
//        String personalBalanceString = "Your balance: " + personalBalance;
//        String balanceString = "Balance of the home: " + homeBalance;
//
//        JLabel personalBalanceLabel = new JLabel();
//        JLabel balanceLabel = new JLabel();
//
//        balanceLabel.setText(balanceString);
//        personalBalanceLabel.setText(personalBalanceString);
//
//        balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
//        personalBalanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
////        balanceLabel.setBounds(120,140,100,100);
//        balanceLabel.setHorizontalTextPosition(JLabel.CENTER);
//        balanceLabel.setVerticalTextPosition(JLabel.CENTER);
//
////        personalBalanceLabel.setHorizontalTextPosition(JLabel.CENTER);
////        personalBalanceLabel.setVerticalTextPosition(JLabel.LEFT);
//
//        this.add(balanceLabel);
////        this.add(personalBalanceLabel);
//
////        balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
////        balanceLabel.setBounds(200,140,100,100);
//
//
////        contentPane.add(personalBalanceLabel);
////        contentPane.add(balanceLabel);
////
////
////
////
////        setContentPane(contentPane);
//
//    }

    private List<ModelPieChart> plotData(User user) {
        ArrayList<ModelPieChart> list = new ArrayList<>();
        List<Expense> expenses = user.getExpenses();

        double[] amounts = new double[CategoryOfExpense.values().length];

        for(var e : expenses) {
//            list.add(new ModelPieChart(e.getCategoryOfExpense().name(), e.getAmount(), e.getCategoryOfExpense().getColor()));
            amounts[e.getCategoryOfExpense().getId()] += e.getAmount();
        }

        for(int i=0;i<amounts.length;i++) {
            list.add(new ModelPieChart(CategoryOfExpense.values()[i].name(), amounts[i], CategoryOfExpense.values()[i].getColor()));
        }

        return list;
    }

    public static void main(String[] args) {
//        UserAdult user = new UserAdult(new HomeAccount(), "sss","swsaw","p","k");
//        user.addExpense(new Expense(21, CategoryOfExpense.GROCERIES, false));
        new UserPage(new UserAdult(new HomeAccount(),"ws","wae","swww","ws"));
//        frame.setVisible(true);
    }



//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//
//            //TODO
//            //odczytywanie z pliku
//
//            //test
//            HomeAccount homeAccount=new HomeAccount();
//            UserAdult u = new UserAdult(homeAccount,"sda","sfa");
//            //end of test
//
//            @Override
//            public void run() {
//
//                new Login(homeAccount);
//
//            }
//        });
//
//    }
}
