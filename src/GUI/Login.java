package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import System.HomeAccount;
import System.User;
import System.UserAdult;



public class Login extends JFrame {
    public static final int W_FRAME = 540;
    public static final int H_FRAME = 360;
    private final String errorText = "Wrong username or password";
    private JPanel contentPane;
    private JButton logButton;
    private JLabel labelUsername, labelPassword, labelIcon, labelErrorText;
    private JTextField textFieldUsername;
    private JPasswordField passwordFieldPassword;
    private Insets insets;
    private HomeAccount homeAccount;

    public Login(HomeAccount ha){
        super("login");
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        insets = this.getInsets();
        homeAccount=ha;
        LoginGUI();

    }

    private void LoginGUI(){
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        labelUsername = new JLabel("Username");
        labelUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelUsername.setBounds(120,140,70,20);
        contentPane.add(labelUsername);

        labelPassword = new JLabel("Password");
        labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelPassword.setBounds(120,180,70,20);
        contentPane.add(labelPassword);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldUsername.setBounds(220,140,120,20);
        textFieldUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordFieldPassword.requestFocus();
            }
        });
        contentPane.add(textFieldUsername);

        passwordFieldPassword = new JPasswordField();
        passwordFieldPassword.setBounds(220, 180,
                120, 20);
        passwordFieldPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logButton.doClick();
            }
        });
        contentPane.add(passwordFieldPassword);

        labelErrorText = new JLabel();
        labelErrorText.setForeground(Color.RED);
        labelErrorText.setBounds(200,250,170,30);
        contentPane.add(labelErrorText);

        logButton = new JButton("Log");
        logButton.setBounds(240, 220,80,30);
        logButton.setFocusPainted(false);
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO
                //czytanie pliku z Userami

                String login = textFieldUsername.getText();
                User userFromLogin = homeAccount.containsLogin(login);
                if (userFromLogin != null){
                    if (userFromLogin.getPassword().equals(String.valueOf(passwordFieldPassword.getPassword()))){

                        //TODO
                        //przejście do nowej strony

                        labelErrorText.setText("");

                        //test
                        labelErrorText.setText("Zalogowano");

                    }
                }
                else {
                    labelErrorText.setText("Incorrect login or password");
                    passwordFieldPassword.setText("");
                }
            }
        });
        contentPane.add(logButton);

        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            //TODO
            //odczytywanie z pliku

            //test
            HomeAccount homeAccount=new HomeAccount();
            UserAdult u = new UserAdult(homeAccount,"sda","sfa");
            //end of test

            @Override
            public void run() {

                new Login(homeAccount);

            }
        });

    }


}
