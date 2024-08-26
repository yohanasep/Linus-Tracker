package GUI;

import javax.swing.JRadioButton;

import java.awt.Font;

public class RadioBtn extends JRadioButton {
    public static JRadioButton initRadioButton(Panel panel, String name, int boundX, int boundY, int width, int height){
        JRadioButton radioButton = new JRadioButton(name);
        radioButton.setBounds(boundX, boundY, width, height);
        radioButton.setText(name);
        radioButton.setFont(new Font("Arial", 0, 16));
        radioButton.setFocusable(false);

        panel.add(radioButton);

        return radioButton;
    }

    public RadioBtn(String name){
        super(name); 
    }
}
