package com.zlm.draw;

import javax.swing.*;
import java.awt.*;

public class Draw_ extends JFrame {
    private myPanel mp = null;

    public static void main(String[] args) {
        new Draw_();
    }

    public Draw_() {
        mp = new myPanel();
        this.add(mp);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class myPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(10, 10, 100, 100);
    }
}
