package Default;

import GUI.*;
import Roles.Halte.*;
import JDBC.JDBC;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.File;

public class Main{
    private final Frame frame;
    private final Panel panel = new Panel("/Images/peta.png");
    JPanel announcementPanel, logHaltePanel;
    JLabel announcementLabel, logHalteLabel;
    JButton loginBtn, announcementBtn, seeLinus1Btn, seeLinus2Btn, refreshBtn;
    int banyakLinusBeroperasi = countLinusToday();
    JDBC Connect = new JDBC();

    private void checkIfAnyAnnouncement(){
        String showListLinusQuery = "SELECT body FROM announcements WHERE time=CURDATE() ORDER BY id DESC LIMIT 1";
        
        try(PreparedStatement state = Connect.createStatement(showListLinusQuery)){
            ResultSet rs = state.executeQuery(showListLinusQuery);

            if (rs.next()) {
                String announcement = rs.getString("body");
               announcementLabel.setText("<HTML>" + announcement);
            } else {
                announcementPanel.setVisible(false);
                announcementBtn.setVisible(false);
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    private void initLogin(){
        new Login();
        frame.dispose();
    }

    public static int countLinusToday() {
        String filePath = "D:\\";
        File directory = new File(filePath);

        File[] files = directory.listFiles();
        int count = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith("LogBus.txt")) {
                    count++;
                }
            }
        }

        return count;
    }

    public Main(){
        frame = new Frame("Linus Tracker", panel);

        initComponents();
        checkIfAnyAnnouncement();

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                initLogin();
            }
        });

        announcementBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
               if(!announcementPanel.isVisible()){
                    announcementBtn.setText("Tutup Pengumuman");
                    announcementPanel.setVisible(true);
               } else {
                    announcementBtn.setText("Lihat Pengumuman");
                    announcementPanel.setVisible(false);
               }
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if (banyakLinusBeroperasi > 0){
                    Halte.readFile(0, logHalteLabel);
                }
            }
        });

        if (banyakLinusBeroperasi > 0){
            Halte.readFile(0, logHalteLabel);

            seeLinus1Btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    Halte.readFile(0, logHalteLabel);
                }
            });

            if (banyakLinusBeroperasi > 1){
                seeLinus2Btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae){
                        Halte.readFile(1, logHalteLabel);
                    }
                });    
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Main());
    }

    private void initComponents(){
        //buttons
        loginBtn = Button.initButton(panel, "Login", 710, 6, 75, 20, "maroon");
        announcementBtn = Button.initButton(panel, "Tutup Pengumuman", 550, 6, 150, 20, "maroon");
        refreshBtn = Button.initButton(panel, "Refresh Lokasi Linus", 20, 470, 180, 30, "orange");


        //for announcement
        announcementPanel = new JPanel();
        announcementPanel.setBackground(Color.BLACK);
        announcementPanel.setBounds(230, 40, 560, 450);
        announcementPanel.setPreferredSize(new Dimension(800, 520));
        announcementPanel.setLayout(null);

        announcementLabel = new JLabel();
        announcementLabel.setVerticalAlignment(JLabel.TOP);
        announcementLabel.setBounds(30, 20, 520, 200);
        announcementLabel.setFont(new Font("Arial", 4, 14));
        announcementLabel.setForeground(Color.WHITE);


        //for log halte
        logHaltePanel = new JPanel();
        logHaltePanel.setBackground(new Color(240, 240, 240, 255));
        logHaltePanel.setBounds(20, 50, 180, 400);
        logHaltePanel.setPreferredSize(new Dimension(800, 520));
        logHaltePanel.setLayout(null);

        logHalteLabel = new JLabel();
        logHalteLabel.setVerticalAlignment(JLabel.TOP);
        logHalteLabel.setBounds(17, 20, 150, 360);
        logHalteLabel.setFont(new Font("Arial", 1, 12));

        if (banyakLinusBeroperasi > 0){
            seeLinus1Btn = Button.initButton(panel, "Linus 1", 240, 6, 75, 20, "maroon");
            if (banyakLinusBeroperasi > 1){
                seeLinus2Btn = Button.initButton(panel, "Linus 2", 327, 6, 75, 20, "maroon");
            }
        } else {
            logHalteLabel.setText("<HTML>Maaf, tidak ada linus yang sedang beroperasi saat ini");
        }
        
        panel.add(announcementPanel);
        panel.add(logHaltePanel);
        announcementPanel.add(announcementLabel);
        logHaltePanel.add(logHalteLabel);
    }
}