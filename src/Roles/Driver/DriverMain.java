package Roles.Driver;

import GUI.*;
import Default.*;
import Roles.Halte.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DriverMain {
    private final Panel panel = new Panel(true);
    private Panel whitePanel;
    private final Frame frame;
    private JButton logoutBtn;
    private JButton tandaiHalteBtn;
    private JButton akhiriBtn;
    private JLabel clockLabel = new JLabel();
    
    private void GUI(){
        //white panel
        whitePanel = new Panel(false);
        whitePanel.setBackground(new Color(240, 240, 240, 255));
        whitePanel.setBounds(0, 40, 800, 480);

        //logout button
        logoutBtn = Button.initButton(panel, "Logout", 710, 7, 75, 25, "maroon");
        
        //list halte text
        JLabel listHalteLabel = new JLabel("List Halte");
        listHalteLabel.setBounds(60, 40, 300, 40);
        listHalteLabel.setFont(new Font("Arial", 1, 24));

        //perintah pilih halte text
        JLabel perintahPilihHalteLabel = new JLabel("Silakan pilih halte pemberhentian anda saat ini");
        perintahPilihHalteLabel.setBounds(60, 90, 400, 20);
        perintahPilihHalteLabel.setFont(new Font("Arial", 4, 18));

        //button akhiri
        akhiriBtn = Button.initButton(panel, "Akhiri", 240, 385, 120, 40, "orange");
        
        //button ok
        tandaiHalteBtn = Button.initButton(panel, "Tandai Halte", 420, 385, 120, 40, "orange");
    
        //warning text
        JLabel warningLabel = new JLabel("<HTML>*PERINGATAN! Hanya tekan tombol AKHIRI apabila linus sudah selesai beroperasi hari ini</HTML>");
        warningLabel.setFont(new Font("Arial", 0, 18));
        warningLabel.setBounds(60, 405, 700, 50);

        //adding all components to panel
        panel.add(whitePanel);
        panel.add(logoutBtn);
        whitePanel.add(clockLabel);
        whitePanel.add(listHalteLabel);
        whitePanel.add(perintahPilihHalteLabel);
        whitePanel.add(warningLabel);
    }

    public DriverMain(){
        frame = new Frame("Dashboard Pengemudi", panel);
        
        GUI();

        //jam
        new Clock(clockLabel);

        //handle penandaan halte by driver
        String username = Login.getUsername();
        Halte.HalteHandler(whitePanel, tandaiHalteBtn, username);

        //akhiri button listener
        akhiriBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin mengakhiri track linus ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        String filePath = "D:\\";
                        String fileName = username + "LogBus.txt";
                        File file = new File(filePath + fileName);
                        boolean cekeksistensi = file.exists();
                        if (cekeksistensi == true) {
                            file.delete();
                        }
                    } catch (Exception e) {
                        System.out.println("gagal delete file");
                        e.printStackTrace();
                    }

                    new Main();
                }
            }
        });

        //logout
        new Logout(frame, logoutBtn);
    }
}
