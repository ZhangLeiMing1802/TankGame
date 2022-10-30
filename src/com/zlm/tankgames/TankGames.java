package com.zlm.tankgames;

import javax.swing.*;

public class TankGames extends JFrame {

    DrawTanksPanel panel = null;

    public TankGames() {
        panel = new DrawTanksPanel();
        this.add(panel);
        this.setSize(DrawTanksPanel.screenWidth, DrawTanksPanel.screenHeight);
        this.addKeyListener(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(500, 200);
    }

    public static void main(String[] args) {
        // new TankGames();

        Runtime runtime = Runtime.getRuntime();
        int i = runtime.availableProcessors();
        System.out.println(i);
    }
}
