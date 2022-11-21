package com.zlm.tankgames;

import javax.swing.*;

public class TankGames extends JFrame {

    DrawTanksPanel panel = null;

    public TankGames() {
        panel = new DrawTanksPanel();
        this.add(panel);
        Thread thread = new Thread(panel);
        thread.start();
        this.setSize(DrawTanksPanel.screenWidth + 50, DrawTanksPanel.screenHeight + 50);
        this.addKeyListener(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(500, 200);
    }

    public static void main(String[] args) {
         new TankGames();
    }
}
