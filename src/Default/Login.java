package Default;

import GUI.*;
import JDBC.*;
import Roles.Admin.AdminMain;
import Roles.Driver.DriverMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;

public class Login{
    private Frame frame;
    private Panel panel = new Panel("/Images/login.png");
    private static JTextField username = new JTextField();
    private JPasswordField password = new JPasswordField();

    private void back(){
        JButton backBtn = Button.initButton(panel, "<- Kembali", 10, 10, 95, 25, "orange");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                new Main();
                frame.dispose();
            }
        });
    }

    private void initLogin(){
        JButton loginBtn = Button.initButton(panel, "Login", 325, 315, 75, 25, "orange");
        panel.add(loginBtn);
        
        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (username.getText().equals("") || password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Username dan password harus diisi!", "Login gagal", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                    checkLogin(username.getText(), new String(password.getPassword()));
            }
        };
    
        loginBtn.addActionListener(loginAction);
        // username.addActionListener(loginAction);
        // password.addActionListener(loginAction);
    }

    private void checkLogin(String username, String password){
        JDBC Connect = new JDBC();
        String loginQuery = "SELECT role FROM users WHERE username=? AND password=?";
        
        try(PreparedStatement state = Connect.createStatement(loginQuery)){
            state.setString(1, username);
            state.setString(2, password);

            ResultSet rs = state.executeQuery();

            if (rs.next()){
                if (rs.getString("role").equals("admin")){
                    new AdminMain();
                } else if(rs.getString("role").equals("driver")){
                    new DriverMain();
                }
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username atau password salah!", "Login gagal", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public Login(){
        frame = new Frame("Masuk", panel);

        //back button
        back();

        username.setBounds(210, 236, 190, 20);
        username.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        password.setBounds(210, 280, 190, 20);
        password.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        panel.add(username);
        panel.add(password);

        //login button
        initLogin();
    }

    public static String getUsername(){
        return username.getText();
    }
}