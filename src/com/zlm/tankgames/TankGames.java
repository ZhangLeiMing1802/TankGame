package com.zlm.tankgames;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankGames extends JFrame {

    DrawTanksPanel panel = null;

    public TankGames() {
        panel = new DrawTanksPanel();
        this.add(panel);
        Thread thread = new Thread(panel);
        thread.start();
        this.setSize(DrawTanksPanel.screenWidth + 50 + 300, DrawTanksPanel.screenHeight + 50);
        this.addKeyListener(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(500, 200);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Record.keepRecord(panel.enemyTanks);
            }
        });
    }

    public static void main(String[] args) {
         new TankGames();
    }
}
