package Default;
import GUI.Frame;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logout {
    public Logout(Frame frame, JButton logoutBtn){
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
            new Main();
            frame.dispose();
        }
        });
    }
}
