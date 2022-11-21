package com.zlm.tankgames;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    //敌方坦克数量
    public static final int enemyTankNum = 3;

    //敌方坦克矩形边的x轴坐标(作为绘制坦克的参照点)
    public static int enemyX = 100;
    //敌方坦克矩形边的y轴坐标(作为绘制坦克的参照点)
    public static int enemyY = 10;

    public Vector<Shot> shots = new Vector<>();

    protected int speed = 3;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    /**
     * 敌方坦克自由移动
     */
    public void move() {
        for (int i = 0; i < 10; i++) {
            switch (direct) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveDown();
                    break;
                case 2:
                    moveLeft();
                    break;
                case 3:
                    moveRight();
                    break;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int num = new Random().nextInt(4);
        setDirect(num);
    }

    @Override
    public void run() {
        while (true) {
            move();
            //System.out.println("shots:" + Arrays.asList(shots));
            if (isLive && shots.size() == 0) {
                Shot shot = initShot();
                shots.add(shot);
                Thread thread = new Thread(shot);
                thread.start();
            }
        }
    }
}
