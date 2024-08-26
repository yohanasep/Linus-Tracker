package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Panel extends JPanel {
    private Image backgroundImage;

    public Panel(boolean colored) {
        if (colored == true){
            this.setBackground(new Color(210, 105, 30));
        } else {
            this.setBackground(new Color(240, 240, 240, 255));
        }
        this.setPreferredSize(new Dimension(800, 520));
        this.setLayout(null);
    }

    public Panel(String imgSource) {
        this.setBackground(new Color(210, 105, 30));
        this.backgroundImage = new ImageIcon(getClass().getResource(imgSource)).getImage();
        this.setPreferredSize(new Dimension(800, 520));
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
