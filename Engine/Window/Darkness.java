package Engine.Window;

import java.awt.Color;

import javax.swing.JPanel;

public class Darkness extends JPanel{
    private int opacity = 0;

    public Darkness(){
        this.setForeground(Color.BLACK);
        this.setSize(500, 500);
        this.setVisible(true);
    }
}
