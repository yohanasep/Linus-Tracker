package GUI;

import java.awt.Color;
import javax.swing.JButton;

public class Button extends JButton {
    public static JButton initButton(Panel panel, String name, int boundX, int boundY, int width, int height, String color) {
        JButton button = new Button(name);
        button.setBounds(boundX, boundY, width, height);

        if (color == "maroon"){
            button.setBackground(new Color(153, 0, 0));
        } else if (color == "orange"){
            button.setBackground(new Color(210, 105, 30));
        }
        
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        button.setFocusable(false);

        panel.add(button);

        return button;
    }

    public Button(String name) {
        super(name);
    }
}
