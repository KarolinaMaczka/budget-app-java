package GUI;
import Chart.ModelPieChart;
import Chart.PieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    // Główna strona wyświetla informacje o koncie; oferuje wyświetlenie historii wydatków i przychodów, wykres wydatków
    // dodanie nowych wydatków, przychodów i inwestycji
    public static final int W_FRAME = 730;
    public static final int H_FRAME = 360;
    private JPanel contentPane;
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
    private JButton logout;

    public UserPage(User u) {
        super("UserPage");
        this.user = u;
        setResizable(false);

        setSize(W_FRAME, H_FRAME);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        UserPageGUI();

    }

    private void UserPageGUI() {
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setSize(W_FRAME, H_FRAME);

        JButton logout = new JButton();
        logout.setText("Log out");
        logout.setFont(new Font("Tahoma", Font.PLAIN, 14));
        logout.setBounds(580, 5, 120, 25);
        logout.setFocusable(false);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObjectOutputStream o = null;
                try {
                    o = new ObjectOutputStream(new FileOutputStream(".\\src\\Data\\HomeAccount"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    o.writeObject(user.getHomeAccount());;
                    o.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Login();
                        UserPage.this.dispose();
                    }
                });
            }
        });
        contentPane.add(logout);

        // Baner witający
        JLabel banner = new JLabel();
        banner.setText("Welcome " + user.getFirstName() + " " + user.getSurname() + "!");
        banner.setFont(new Font("Comic sans", Font.BOLD, 24));
        banner.setBounds(5, 5, 400, 30);
        contentPane.add(banner);


        // Przycisk odświeża wprowadzone zmiany
        JButton refresh = new JButton();
        refresh.setText("Refresh");
        refresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
        refresh.setBounds(450, 5, 120, 25);
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



        String personalBalanceString = "Your balance: " + Math.round( user.getPersonalBalanceDateRange(start, end.minusDays(1)) * 100) / 100d + "pln";

        String balanceString = "Balance of the home: " +
                Math.round(100 * user.getHomeAccount().getBalanceDateRange(start, end)) / 100d + "pln";
        String investmentString = "Amount invested: " +
                Math.round(100*user.getAmountInvested() ) / 100d + "pln";

        // Labelki z informacjami o koncie własnym, domowym i inwestycjach
        JLabel personalBalanceLabel = new JLabel();
        JLabel balanceLabel = new JLabel();
        JLabel investmentLabel = new JLabel();

        investmentLabel.setText(investmentString);
        balanceLabel.setText(balanceString);
        personalBalanceLabel.setText(personalBalanceString);

        balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        personalBalanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        investmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        balanceLabel.setBounds(5,20 + h,300,20);
        personalBalanceLabel.setBounds(5,40 + h,300,20);
        investmentLabel.setBounds(5, 60 + h, 300, 20);

        contentPane.add(balanceLabel);
        contentPane.add(personalBalanceLabel);
        contentPane.add(investmentLabel);


        // tabelka z 10 ostatnimi wydatkami i przychodami
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

        updateTable(); //tu sie robi tabelka i dodaje do contentPane

        String months[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String years[] = {"2018","2019","2020","2021","2022","2023"};

        //wybór zakresu dat
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
        startDateLabel.setBounds(0,80 + h,140,20);
        endDateLabel.setBounds(140,80 + h,140,20);

        contentPane.add(startDateLabel);
        contentPane.add(endDateLabel);

        // ustawia zakres dat na ostatni miesiąc
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


        // wykres wydatków
        JButton pieChart = new JButton();
        pieChart.setBounds(40, 155 + h, 170,30);
        pieChart.setText("Display plot");
        pieChart.setFont(new Font("Tahoma", Font.BOLD, 18));
        pieChart.setFocusable(false);

        pieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == pieChart) {
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
                }
            }
        });

        contentPane.add(pieChart);

        // gdy zaznaczone, to wykres wydatków jest z danych całego domu
        JCheckBox typeOfPlot = new JCheckBox();
        typeOfPlot.setText("Whole house");
        typeOfPlot.setBounds(210, 125 + h, 100, 20);

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

        // otwiera nowy panel z historią wydatków i przychodów
        JButton extendedHistory = new JButton();
        extendedHistory.setBounds(20, 195 + h, 210, 30);
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
                }
            }
        });
        contentPane.add(extendedHistory);

        // dodawanie wydatków itd, otwiera nowe panele
        JButton addExpense = new JButton();
        JButton addIncome = new JButton();
        JButton addInvestment = new JButton();
        JButton removeInvestment = new JButton();


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

        // otwiera nowy panel dodajacy nowych uzytkownikow
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
        // genetuje dane do wykresu
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
        String[] columnNames = {"Incomes", "Expenses"};

        String[] expenses = new String[10];
        String[] incomes = new String[10];

        for(int i=0; i<10;i++) {
            incomes[i] = "";
            expenses[i] = "";
        }

        int counter = 0;
        for(var expense : user.getExpenses()) {
            if((expense.getDate().isBefore(end) && expense.getDate().isAfter(start)) || expense.getDate().isEqual(start) || expense.getDate().isEqual(end)) {
                expenses[counter] = expense.toString();
                counter ++;
                if(counter > 9)
                    break;
            }
        }
        counter = 0;
        for(var income : user.getIncomings()) {
            if((income.getDate().isBefore(end) && income.getDate().isAfter(start)) || income.getDate().isEqual(start) || income.getDate().isEqual(end)) {
                incomes[counter] = income.toString();
                counter ++;
                if(counter > 9)
                    break;
            }

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




}
