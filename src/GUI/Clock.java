package GUI;

import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Calendar;

public class Clock extends JLabel {
    public Clock(JLabel clockLabel){
        clockLabel.setBounds(705, 13, 75, 25);
        clockLabel.setFont(new Font("Arial", 1, 18));
        clockLabel.setText("00:00:00");
    
        ActionListener timer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                Calendar cl = Calendar.getInstance();
                int hour = cl.get(Calendar.HOUR_OF_DAY);
                int minute = cl.get(Calendar.MINUTE);
                int second = cl.get(Calendar.SECOND);

                String Strhour= String.format("%02d", hour);
                String Strminute = String.format("%02d", minute);
                String Strsecond = String.format("%02d", second);

                clockLabel.setText(Strhour + ":" + Strminute + ":" + Strsecond);
            }
        };
        
        new Timer(1000, timer).start();
    }
}
