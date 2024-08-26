package Roles.Halte;

import GUI.Panel;
import GUI.RadioBtn;

import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Halte {
    private String name, time;
    private static LocalTime now = LocalTime.now();
    private static Deque<Halte> halteStack = new ArrayDeque<>();
    private static Set<Halte> writtenHalte = new HashSet<>(); 
    private final static String filePath = "D:\\";

    public static void saveToFile(String username) {
        if (halteStack.isEmpty()) {
            System.out.println("Empty file");
        } else {
            // Alamat file
            String fileName = username + "LogBus.txt";
            File file = new File(filePath + fileName);
    
            try (FileWriter writer = new FileWriter(file, true)) { 
                Iterator<Halte> iterator = halteStack.iterator();
    
                while (iterator.hasNext()) {
                    Halte halte = iterator.next();
                    if (!writtenHalte.contains(halte)) {
                        writer.write(halte.getName() + " | " + halte.getTime() + "<br>\n");
                        writtenHalte.add(halte);

                        JOptionPane.showMessageDialog(null, "Halte berhasil ditandai!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    public static void readFile(int fileIndex, JLabel logHalteLabel) {
        File directory = new File(filePath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith("LogBus.txt"));
        File fileLog = files[fileIndex];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileLog))) {
            StringBuilder content = new StringBuilder();
            String text;

            while ((text = reader.readLine()) != null) {
                content.append(text);
            }

            logHalteLabel.setText("<HTML>" + content.toString() + "</HTML>");
        } catch (IOException e) {
            System.out.println("Error reading file : " + e.getMessage());
        }

    }
    
    public static void HalteHandler(Panel panel, JButton tandaiHalteBtn, String username){
        // Define halte names and their bounds
        String[] halteNames = {"Halte Pintu 4", "Halte Farmasi", "Halte FMIPA", "Halte FEB", "Halte FISIP", 
                               "Halte GEMA", "Halte FIB", "Halte FH", "Halte FK", "Halte Fasilkomti"};
        int[][] bounds = {
            {60, 135, 115, 30}, {60, 185, 125, 30}, {60, 235, 115, 30}, {60, 285, 100, 30},
            {325, 135, 115, 30}, {325, 185, 115, 30}, {325, 235, 92, 30}, {325, 285, 90, 30},
            {550, 135, 90, 30}, {550, 185, 140, 30}
        };
    
        ButtonGroup btnGroup = new ButtonGroup();
        JRadioButton[] radioButtonHalteNames = new JRadioButton[halteNames.length];
    
        for(int i = 0; i < halteNames.length; i++){
            radioButtonHalteNames[i] = RadioBtn.initRadioButton(panel, halteNames[i], 
                                                                bounds[i][0], bounds[i][1], 
                                                                bounds[i][2], bounds[i][3]);
            btnGroup.add(radioButtonHalteNames[i]);
        }
        
        tandaiHalteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                boolean halteSelected = false;
                for(int i = 0; i < halteNames.length; i++){
                    if(radioButtonHalteNames[i].isSelected()){
                        String selectedHalteName = halteNames[i];

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String selectedHalteTime = now.format(formatter);

                        Halte halte = new Halte(selectedHalteName, selectedHalteTime);
                        halteStack.push(halte);
    
                        saveToFile(username);
                        halteSelected = true;
                        break;
                    }
                }
    
                if(!halteSelected){
                    JOptionPane.showMessageDialog(null, "Silakan pilih halte terlebih dahulu!");
                }
            }
        });
    }

    public Halte(){}
    
    public Halte(Panel panel, JButton button, String username){
        HalteHandler(panel, button, username);
    }

    public Halte(String name, String time){
        this.name = name;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public String getTime(){
        return time;
    }
}
