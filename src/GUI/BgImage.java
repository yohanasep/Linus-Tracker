package GUI;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class BgImage extends JLabel{
    public BgImage(String ImgSource) {
        setIcon(new ImageIcon(getClass().getResource(ImgSource)));
        setBounds(0, 0, 800, 520);
    }
}
