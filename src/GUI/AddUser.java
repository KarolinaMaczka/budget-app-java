package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import System.*;

public class AddUser extends JFrame {
    public static final int W_FRAME = 700;
    public static final int H_FRAME = 350;
    private JPanel contentPaneFL;
    private JButton newAccountButton;
    private JLabel labelUsername, labelPassword, labelErrorText,labelFirstName,labelSurname;
    private JLabel errorUsername,errorPassword,errorFirstName,errorSurname;
    private JTextField textFieldUsername, textFieldPassword, textFieldFirstName,textFieldSurname;
    private Insets insets;
    private HomeAccount homeAccount;
    private JLabel title;
    private JCheckBox childCheckbox;
    private User u;

    public AddUser(HomeAccount homeAccount) {
        super("login");
        this.homeAccount =homeAccount;
        setResizable(false);
        setLayout(null);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setLocation(getX() - 80, getY() - 80);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        insets = this.getInsets();
        AddUserGUI();

    }

    private void AddUserGUI(){
        this.setTitle("Creating new account");
        contentPaneFL = new JPanel();

        GroupLayout layout = new GroupLayout(contentPaneFL);
        contentPaneFL.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        contentPaneFL.setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        labelFirstName = new JLabel("First Name",SwingConstants.CENTER);
        labelFirstName.setVerticalAlignment(SwingConstants.CENTER);
        labelFirstName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelFirstName.setSize(170,20);

        labelSurname = new JLabel("Surname",SwingConstants.CENTER);
        labelSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelSurname.setSize(170,20);

        labelUsername = new JLabel("Username",SwingConstants.CENTER);
        labelUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelUsername.setSize(170,20);

        labelPassword = new JLabel("Password",SwingConstants.CENTER);
        labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelPassword.setSize(170,20);

        errorFirstName=new JLabel("",SwingConstants.CENTER);
        errorFirstName.setSize(70,20);
        errorFirstName.setForeground(Color.RED);

        errorSurname=new JLabel("");
        errorSurname.setSize(70,20);
        errorSurname.setForeground(Color.RED);

        errorUsername=new JLabel("");
        errorUsername.setSize(70,20);
        errorUsername.setForeground(Color.RED);

        errorPassword=new JLabel("");
        errorPassword.setSize(70,20);
        errorPassword.setForeground(Color.RED);

        title = new JLabel("Add new user to your home account");
        title.setFont(new Font("Helvetica", Font.PLAIN, 18));
        title.setSize(70,20);

        textFieldFirstName = new JTextField();
        textFieldFirstName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textFieldFirstName.setSize(120,20);
        textFieldFirstName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldFirstName.getText().length()>14){
                    errorFirstName.setText("Your first name must be shorter than 15 characters");
                }else{
                    errorFirstName.setText("");
                }
                textFieldSurname.requestFocus();
            }
        });
        textFieldSurname = new JTextField();
        textFieldSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textFieldSurname.setSize(120,20);
        textFieldSurname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldSurname.getText().length()>14){
                    errorSurname.setText("Your surname must be shorter than 15 characters");
                }else{
                    errorSurname.setText("");
                }
                textFieldUsername.requestFocus();
            }
        });
        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textFieldUsername.setSize(120,20);
        textFieldUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldUsername.getText().length()>14){
                    errorUsername.setText("Your username must be shorter than 15 characters");
                }else{
                    errorUsername.setText("");
                }
                textFieldPassword.requestFocus();
            }
        });
        textFieldPassword = new JTextField();
        textFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textFieldPassword.setSize(120,20);
        textFieldPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldPassword.getText().length()>14){
                    errorPassword.setText("Your password must be shorter than 15 characters");
                } else if (textFieldPassword.getText().length()<9){
                    errorPassword.setText("Your password must be longer than 8 characters");
                }else{
                    errorPassword.setText("");
                }
            }
        });
        labelErrorText=new JLabel("");
        labelErrorText.setSize(70,20);
        labelErrorText.setForeground(Color.RED);

        childCheckbox =new JCheckBox("Child User");
        childCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                childCheckbox.setSelected(true);
            }
        });

        newAccountButton=new JButton("Create Account");
        newAccountButton.setSize(70,20);
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkDataCorrectness(textFieldFirstName.getText(),textFieldSurname.getText(),textFieldUsername.getText(),textFieldPassword.getText())){
                    labelErrorText.setForeground(Color.GREEN);
                    labelErrorText.setText("Tworzenie konta");
                    if (childCheckbox.isSelected()){
                        u=new UserChild(homeAccount,textFieldPassword.getText(),textFieldUsername.getText(),textFieldFirstName.getText(),textFieldSurname.getText());
                    }else {
                        u = new UserAdult(homeAccount, textFieldPassword.getText(), textFieldUsername.getText(), textFieldFirstName.getText(), textFieldSurname.getText());
                    }

                    System.out.println(u);
                    System.out.println(homeAccount.getUsers());

                    ObjectOutputStream o = null;
                    try {
                        o = new ObjectOutputStream(new FileOutputStream(".\\src\\Data\\HomeAccount"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        o.writeObject(homeAccount);;
                        o.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            AddUser.this.dispose();
                        }
                    });
                }
            }
        });

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(title)
                                .addComponent(errorFirstName,GroupLayout.Alignment.LEADING)
                                .addComponent(labelFirstName,GroupLayout.Alignment.TRAILING)
                                .addComponent(errorSurname)
                                .addComponent(labelSurname,GroupLayout.Alignment.TRAILING)
                                .addComponent(errorUsername)
                                .addComponent(labelUsername,GroupLayout.Alignment.TRAILING)
                                .addComponent(errorPassword)
                                .addComponent(labelPassword,GroupLayout.Alignment.TRAILING)
                                .addComponent(labelErrorText)
                                .addComponent(childCheckbox)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(textFieldFirstName)
                                .addComponent(textFieldSurname)
                                .addComponent(textFieldUsername)
                                .addComponent(textFieldPassword)
                                .addComponent(newAccountButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(title)
                        .addComponent(errorFirstName)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelFirstName)
                                .addComponent(textFieldFirstName)
                        )
                        .addComponent(errorSurname)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelSurname)
                                .addComponent(textFieldSurname)
                        )
                        .addComponent(errorUsername)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelUsername)
                                .addComponent(textFieldUsername)
                        )
                        .addComponent(errorPassword)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelPassword)
                                .addComponent(textFieldPassword)
                        )
                        .addComponent(childCheckbox)
                        .addComponent(labelErrorText)
                        .addComponent(newAccountButton)
        );

        setContentPane(contentPaneFL);
    }

    private boolean checkDataCorrectness(String firstName,String surname, String username,String password){
        if (firstName.length()==0 || surname.length()==0 || username.length()==0 || password.length()==0 ){
            labelErrorText.setText("Wpisz poprawne dane");
            return false;
        }else if (firstName.length()>14 || surname.length()>14 || username.length()>14 || password.length()>14||password.length()<9){
            labelErrorText.setText("Wpisz poprawne dane");
            return false;
        }
        labelErrorText.setText("");
        return true;
    }

//    public static void main(String[] args) {
//
//        File homeAccountFile= new File("C:\\Users\\karim\\IdeaProjects\\kontrola-budzetu\\src\\Data\\HomeAccount");
//
//        if (homeAccountFile.length()==0){
//            System.out.println("pusty");
//        }else {
//            ObjectInputStream in = null;
//            try {
//                in = new ObjectInputStream(new FileInputStream(homeAccountFile));
//            } catch (IOException e) {
//                System.out.println("zła ścieżka");
//            }
//
//            try {
//                HomeAccount homeAccount =(HomeAccount) in.readObject();
//                System.out.println(homeAccount);
//                System.out.println(homeAccount.getUsers());
//                EventQueue.invokeLater(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        new AddUser(homeAccount);
//
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
}
