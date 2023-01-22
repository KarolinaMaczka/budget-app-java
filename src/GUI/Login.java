package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import System.*;

public class Login extends JFrame {
    public static final int W_FRAME = 700;
    public static final int H_FRAME = 250;
    private final String errorText = "Wrong username or password";
    private JPanel contentPane,contentPaneFL;
    private JButton logButton,newAccountButton;
    private JLabel labelUsername, labelPassword, labelErrorText,labelFirstName,labelSurname;
    private JLabel errorUsername,errorPassword,errorFirstName,errorSurname;
    private JTextField textFieldUsername, textFieldPassword, textFieldFirstName,textFieldSurname;
    private JPasswordField passwordFieldPassword;
    private Insets insets;
    private HomeAccount homeAccount;
    private JLabel title;

    public Login() {
        super("login");
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        insets = this.getInsets();

        //reading from file
        File homeAccountFile= new File("C:\\Users\\karim\\IdeaProjects\\kontrola-budzetu\\src\\Data\\HomeAccount");
        //if there is no home account yet
        if (homeAccountFile.length()==0){
            Login.this.dispose();
            new FirstLogin();
        }else {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(homeAccountFile));
            } catch (IOException e) {
                System.out.println("zła ścieżka");
            }

            try {
                homeAccount =(HomeAccount) in.readObject();
                System.out.println(homeAccount);
                System.out.println(homeAccount.getUsers());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            LoginGUI();
        }

    }



    private void LoginGUI(){
        this.setTitle("Logging");
        contentPane = new JPanel();
        contentPane.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        labelUsername = new JLabel("Username");
        labelUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelUsername.setSize(70,20);

        labelPassword = new JLabel("Password");
        labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelPassword.setSize(70,20);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldUsername.setSize(120,20);
        textFieldUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordFieldPassword.requestFocus();
            }
        });

        passwordFieldPassword = new JPasswordField();
        passwordFieldPassword.setSize(120,20);
        passwordFieldPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logButton.doClick();
            }
        });

        labelErrorText = new JLabel("");
        labelErrorText.setForeground(Color.RED);
        labelErrorText.setSize(170,30);

        logButton = new JButton("Sign in");
        logButton.setSize(80,30);
        logButton.setFocusPainted(false);
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObjectInputStream in = null;
                try {
                    in = new ObjectInputStream(new FileInputStream("C:\\Users\\karim\\IdeaProjects\\kontrola-budzetu\\src\\Data\\HomeAccount"));
                } catch (IOException exc) {
                    System.out.println("zła ścieżka");
                }

                try {
                    homeAccount =(HomeAccount) in.readObject();
                } catch (IOException exc) {
                    exc.printStackTrace();
                } catch (ClassNotFoundException exc) {
                    exc.printStackTrace();
                }

                String login = textFieldUsername.getText();
                User userFromLogin = homeAccount.containsLogin(login);
                if (userFromLogin != null){
                    if (userFromLogin.getPassword().equals(String.valueOf(passwordFieldPassword.getPassword()))){

                        labelErrorText.setText("");
                        Login.this.dispose();

                        if (userFromLogin instanceof UserAdult){
                            new UserPage(userFromLogin);
                        }else{
                            new ChildUserPage(userFromLogin);
                        }
                    }
                }
                else {
                    labelErrorText.setText("Incorrect login or password");
                    passwordFieldPassword.setText("");
                }
            }
        });

        title = new JLabel("Log into your home account");
        title.setFont(new Font("Helvetica", Font.PLAIN, 18));
        title.setSize(70,20);

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(title)
                                .addComponent(labelUsername,GroupLayout.Alignment.TRAILING)
                                .addComponent(labelPassword,GroupLayout.Alignment.TRAILING)
                                .addComponent(labelErrorText)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(textFieldUsername)
                                .addComponent(passwordFieldPassword)
                                .addComponent(logButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(title)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelUsername)
                                .addComponent(textFieldUsername)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelPassword)
                                .addComponent(passwordFieldPassword)
                        )
                        .addComponent(labelErrorText)
                        .addComponent(logButton)
        );

        setContentPane(contentPane);
    }

    public static void main(String[] args) {

        //TODO
        // usuń
        try {
            deleteAccount();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                new Login();

            }
        });

    }

    private static void deleteAccount() throws FileNotFoundException {
        //TODO
        // usuń czyszczenie pliku
        PrintWriter writer = new PrintWriter("C:\\Users\\karim\\IdeaProjects\\kontrola-budzetu\\src\\Data\\HomeAccount");
        writer.print("");
        // other operations
        writer.close();
        // koniec usuwania
    }


}
