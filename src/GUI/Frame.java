package GUI;

import javax.swing.JFrame;

public class Frame extends JFrame{
    private void FrameSetUp(){
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public Frame(String title, Panel panel){
        super(title);
        this.add(panel);
        FrameSetUp();
    }

    public Frame(String title, String ImgSource){
        super(title);
        this.add(new Panel(ImgSource));
        FrameSetUp();
    }
}
