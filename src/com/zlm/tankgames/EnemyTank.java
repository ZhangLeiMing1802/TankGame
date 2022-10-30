package com.zlm.tankgames;

public class EnemyTank extends Tank {
    //敌方坦克数量
    public static final int enemyTankNum = 3;

    //敌方坦克矩形边的x轴坐标(作为绘制坦克的参照点)
    public static int enemyX = 100;
    //敌方坦克矩形边的y轴坐标(作为绘制坦克的参照点)
    public static int enemyY = 0;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

}
