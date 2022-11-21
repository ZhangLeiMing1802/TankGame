package com.zlm.tankgames;

import java.util.Vector;

/**
 * 我的坦克
 */
public class Hero extends Tank {
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    /**
     *射击敌方坦克
     */
    public void shotEnemyTank() {
        if (shots.size() == 5) {
            return;
        }
        shot = initShot();
        shots.add(shot);
        Thread thread = new Thread(shot);
        thread.start();
    }

}
