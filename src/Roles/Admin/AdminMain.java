package Roles.Admin;

import GUI.*;
import JDBC.JDBC;
import Default.*;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AdminMain {
    private final Panel panel = new Panel(true);
    private final Frame frame;
    JButton logoutBtn;
    JLabel clockLabel = new JLabel();
    JTabbedPane tabPane = new JTabbedPane();
    JLabel welcomeLabel, jlhLinusBeroperasiLabel, jlhLinusBeroperasi, announcementLabel, dataBusLabel, usernameBusLabel, passwordBusLabel, usernameBusToDelLabel, listLinus;
    JTextField usernameBus, usernameBusToDel;
    JPasswordField passwordBus;
    JTextArea announcement;
    JDBC Connect = new JDBC();

    // for tabbed pane
    Panel addAnnouncementPanel = new Panel(false);
    Panel busDataPanel = new Panel(false);
    Panel addBusPanel = new Panel(false);
    Panel deleteBusPanel  = new Panel(false);

    //button refresh data linus
    JButton refreshDataLinus = Button.initButton(busDataPanel, "Refresh", 300, 205, 90, 35, "orange");

    //button submit announcement
    JButton submitAnnouncement = Button.initButton(addAnnouncementPanel, "Submit", 529, 220, 90, 35, "orange");

    //button submit bus
    JButton submitBus = Button.initButton(addBusPanel, "Submit", 527, 185, 90, 40, "orange");
    
    //button delete bus
    JButton deleteBus = Button.initButton(deleteBusPanel, "Hapus", 527, 100, 90, 40, "orange");

    private void dataLinus(){
        String showListLinusQuery = "SELECT username FROM users WHERE role = 'driver'";
        
        try(PreparedStatement state = Connect.createStatement(showListLinusQuery)){
            ResultSet rs = state.executeQuery(showListLinusQuery);

            StringBuilder usernames = new StringBuilder();

            boolean isLast = false;
            while (rs.next()) {
                String username = rs.getString("username");
                isLast = rs.isLast();
                usernames.append(username);
                if (!isLast) {
                    usernames.append(", \n");
                }
            }

            listLinus.setText(usernames.toString());
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    private void addAnnouncement(String body){
        LocalDate time = LocalDate.now();
        Date sqlTime = Date.valueOf(time);

        String addAnnouncementQuery = "INSERT INTO announcements (body, time) VALUES (?, ?)";
        
        try(PreparedStatement state = Connect.createStatement(addAnnouncementQuery)){
            state.setString(1, body);
            state.setDate(2, sqlTime);

            int rowsAffected = state.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Pengumuman berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Oops sepertinya terjadi kesalahan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    private void addBus(String username, String password){
        String countUserQuery = "SELECT COUNT(username) AS countEntry FROM users WHERE username=?";
        
        try(PreparedStatement state = Connect.createStatement(countUserQuery)){
            state.setString(1, username);

            ResultSet rs = state.executeQuery();

            int countEntry = 0;

            if (rs.next()){
                countEntry = rs.getInt("countEntry");

                if (countEntry > 0){
                    JOptionPane.showMessageDialog(null, "Data bus sudah tersedia!", "Gagal", JOptionPane.ERROR_MESSAGE);
                } else {
                    String addBusQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

                PreparedStatement addBuStatement = Connect.createStatement(addBusQuery);
                addBuStatement.setString(1, username);
                addBuStatement.setString(2, password);

                addBuStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data bus berhasil ditambahkan ke database!", "Sukses", JOptionPane.INFORMATION_MESSAGE);  
                }
            } 
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    private void delBus(String username){
        String countUserQuery = "SELECT COUNT(username) AS countEntry FROM users WHERE username=?";
        
        try(PreparedStatement state = Connect.createStatement(countUserQuery)){
            state.setString(1, username);

            ResultSet rs = state.executeQuery();

            int countEntry = 0;

            if (rs.next()){
                countEntry = rs.getInt("countEntry");

                if (countEntry == 0){
                    JOptionPane.showMessageDialog(null, "Data bus tidak tersedia!", "Gagal", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data linus '" + username + "'?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

                    if(confirm == JOptionPane.YES_OPTION){
                        String delBusQuery = "DELETE FROM users WHERE username=?";

                        PreparedStatement delBuStatement = Connect.createStatement(delBusQuery);
                        delBuStatement.setString(1, username);
        
                        delBuStatement.executeUpdate();
        
                        JOptionPane.showMessageDialog(null, "Data bus berhasil dihapus dari database!", "Sukses", JOptionPane.INFORMATION_MESSAGE);  
                    }
                }
            } 
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public AdminMain(){
        frame = new Frame("Dashboard Admin", panel);
        
        initComponents();

        //jam
        new Clock(clockLabel);

        dataLinus();
        
        refreshDataLinus.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                dataLinus();
            }
        });

        //submit bus button listener
        ActionListener submitBusListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (usernameBus.getText().equals("") || passwordBus.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Username dan password harus diisi!", "Gagal", JOptionPane.ERROR_MESSAGE);
                } else {
                    addBus(usernameBus.getText(), new String(passwordBus.getPassword()));
                }
            }
        };
        submitBus.addActionListener(submitBusListener);
        usernameBus.addActionListener(submitBusListener);
        passwordBus.addActionListener(submitBusListener);

        //delete bus button listener
        ActionListener deleteBusListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (usernameBusToDel.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Masukkan username bus yang ingin dihapus!", "Gagal", JOptionPane.ERROR_MESSAGE);
                } else {
                    delBus(usernameBusToDel.getText());
                }
            }
        };
        deleteBus.addActionListener(deleteBusListener);
        usernameBusToDel.addActionListener(deleteBusListener);

        //submit announcement button listener
        submitAnnouncement.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (announcement.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Isi pengumuman terlebih dahulu!", "Gagal", JOptionPane.ERROR_MESSAGE);
                } else {
                    addAnnouncement(announcement.getText());
                }
            }
        });
        
        //logout
        new Logout(frame, logoutBtn);
    }

    private void initComponents(){
        //white panel
        Panel whitePanel = new Panel(false);
        whitePanel.setBackground(new Color(240, 240, 240, 255));
        whitePanel.setBounds(0, 40, 800, 480);

        //logout button
        logoutBtn = Button.initButton(panel, "Logout", 710, 7, 75, 25, "maroon");
        
        //welcome text
        welcomeLabel = new JLabel("Selamat datang, Admin!");
        welcomeLabel.setBounds(25, 40, 300, 40);
        welcomeLabel.setFont(new Font("Arial", 1, 24));

        //jumlah linus yang sedang beroperasi
        jlhLinusBeroperasiLabel = new JLabel("Banyak linus yang sedang beroperasi : ");
        jlhLinusBeroperasiLabel.setBounds(25, 90, 400, 20);
        jlhLinusBeroperasiLabel.setFont(new Font("Arial", 4, 18));

        jlhLinusBeroperasi = new JLabel(String.valueOf(Main.countLinusToday()));
        jlhLinusBeroperasi.setBounds(340, 90, 100, 20);
        jlhLinusBeroperasi.setFont(new Font("Arial", 4, 18));


        //data bus text
        dataBusLabel = new JLabel("Data Linus : ");
        dataBusLabel.setBounds(100, 0, 500, 100);
        dataBusLabel.setFont(new Font("Arial", 1, 18));        

        listLinus = new JLabel();
        listLinus.setBounds(220, 0, 500, 100);
        listLinus.setFont(new Font("Arial", 0, 18));
        

        //tabbed pane
        tabPane.setBounds(30, 140, 735, 300);
        tabPane.add("Tambah Pengumuman", addAnnouncementPanel);
        tabPane.add("Data Bus", busDataPanel);
        tabPane.add("Tambah Bus", addBusPanel);
        tabPane.add("Hapus Bus", deleteBusPanel);
        

        // for adding new bus data
        usernameBusLabel = new JLabel("Username");
        usernameBusLabel.setBounds(120, 10, 180, 50);
        usernameBusLabel.setFont(new Font("Arial", 1, 16));

        usernameBus = new JTextField();
        usernameBus.setBounds(120, 50, 500, 35);
        usernameBus.setFont(new Font("Arial", 0, 16));

        passwordBusLabel = new JLabel("Password");
        passwordBusLabel.setBounds(120, 100, 180,20);
        passwordBusLabel.setFont(new Font("Arial", 1, 16));

        passwordBus = new JPasswordField();
        passwordBus.setBounds(120, 125, 500, 35);
        passwordBus.setFont(new Font("Arial", 0, 16));

       
        //for deleting bus data
        usernameBusToDelLabel = new JLabel("Username");
        usernameBusToDelLabel.setBounds(120, 10, 180, 50);
        usernameBusToDelLabel.setFont(new Font("Arial", 1, 16));

        usernameBusToDel = new JTextField();
        usernameBusToDel.setBounds(120, 50, 500, 35);
        usernameBusToDel.setFont(new Font("Arial", 0, 16));


        //for adding announcement
        announcementLabel = new JLabel("Pengumuman");
        announcementLabel.setBounds(120, 5, 180, 50);
        announcementLabel.setFont(new Font("Arial", 1, 16));

        announcement = new JTextArea();
        announcement.setWrapStyleWord(true);
        announcement.setLineWrap(true);
        announcement.setBounds(120, 50, 500, 150);
        announcement.setFont(new Font("Arial", 0, 16));
        announcement.setBorder(new EmptyBorder(10, 10, 10, 10));

        
        //adding all components to panel
        panel.add(whitePanel);
        panel.add(logoutBtn);
        whitePanel.add(clockLabel);
        whitePanel.add(welcomeLabel);
        whitePanel.add(jlhLinusBeroperasiLabel);
        whitePanel.add(jlhLinusBeroperasi);
        whitePanel.add(tabPane);

        //for adding announcement
        addAnnouncementPanel.add(announcementLabel);
        addAnnouncementPanel.add(announcement);

        //for data linus
        busDataPanel.add(dataBusLabel);
        busDataPanel.add(listLinus);

        //for adding new bus data
        addBusPanel.add(usernameBus);
        addBusPanel.add(passwordBus);
        addBusPanel.add(usernameBusLabel);
        addBusPanel.add(passwordBusLabel);

        //for deleting bus data
        deleteBusPanel.add(usernameBusToDelLabel);
        deleteBusPanel.add(usernameBusToDel);
    }
}
