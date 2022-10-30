package com.zlm.tankgames;

import java.awt.*;

public class TankOld {
    // x坐标
    private int x;
    // y坐标
    private int y;

    // 坦克方向
    private int direct;
    // 坦克移动速度
    private int speed = 3;

    // 坦克类型，0：我方，1：敌方
    private int type = 0;

    //敌方坦克数量
    private int enemyTankNum = 3;

    public TankOld(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //坦克矩形边的x轴坐标(作为绘制坦克的参照点)
    public static int tankX = 100;
    //坦克矩形边的y轴坐标(作为绘制坦克的参照点)
    public static int tankY = 100;

    //坦克矩形边的x轴坐标(作为绘制坦克的参照点)
    public static int enemyTankX = 30;
    //坦克矩形边的y轴坐标(作为绘制坦克的参照点)
    public static int enemyTankY = 30;

    //坦克的矩形边的宽度
    private int tankRectWidth = 10;
    //坦克的矩形边的高度
    private int tankRectHeight = 60;
    //坦克身体宽度
    private int tankBodyWidth = 20;
    //坦克身体高度
    private int tankBodyHeight = 30;

    // 坦克圆盖子大小
    private int tankOvalWidth = 20;

    // 坦克炮管长度
    private int tankTubeLength = 20;

    /**
     * @param g        画笔
     * @param tankType 坦克类型(0:我方坦克，1:敌方坦克)
     * @param direct   坦克方向(0:向上，1:向下，2:向左，3:向右)
     */
    public void drawTank(Graphics g, int tankType, int direct) {
        switch (tankType) {
            //创建我方坦克
            case 0:
                g.setColor(Color.yellow);
                break;
            // 创建敌方坦克
            case 1:
                g.setColor(Color.cyan);
                break;
        }

        switch (direct) {
            //坦克方向：向上或下
            case 0:
            case 1:
                drawUpDown(g, direct);
                break;
            // 坦克方向：向左或右
            case 2:
            case 3:
                drawLeftRight(g, direct);
                break;
        }
    }

    /**
     * 绘制向上或向下坦克
     *
     * @param g      画笔
     * @param direct 方向-> 0:向上，1：向下
     */
    public void drawUpDown(Graphics g, int direct) {
        /**
         ▊  ▏ ▊
         ▊▇○▇▉
         ▊    ▊
         */
        // 绘制坦克左侧矩形
        g.fill3DRect(x, y, tankRectWidth, tankRectHeight, false);

        /**
         坦克的右侧矩形x坐标为：初始坐标 + 坦克矩形边的宽度 +  坦克身体宽度
         坦克的右侧矩形y坐标和左侧相同，即初始y坐标
         */
        // 绘制坦克右侧矩形
        g.fill3DRect(x + tankRectWidth + tankBodyWidth, y, tankRectWidth, tankRectHeight, false);

        /**
         坦克身体x轴坐标：初始坐标 + 坦克矩形边的宽度
         坦克身体y轴坐标：保证身体在中间位置，y = 初始坐标 + (坦克矩形边的高度 - 坦克身体的高度) / 2
         */
        // 绘制坦克身体
        int tankBodyY = y + (tankRectHeight - tankBodyHeight) / 2;
        g.fill3DRect(x + tankRectWidth, tankBodyY, tankBodyWidth, tankBodyHeight, false);

        /**
         保证坦克圆盖子在身体矩形中间位置，距离矩形身体(20*30) 上下各留距离5
         坦克圆盖子x轴坐标：初始x坐标 + 坦克矩形边的宽度
         坦克圆盖子y轴坐标：坦克身体坐标 + (tankBodyHeight - tankOvalWidth) / 2
         */
        // 绘制坦克圆盖子
        int tankOvalY = tankBodyY + (tankBodyHeight - tankOvalWidth) / 2;
        g.fillOval(x + tankRectWidth, tankOvalY, tankOvalWidth, tankOvalWidth);


        /**
         先写出炮管向上坐标逻辑
         炮管的起始点x在坦克身体居中，startX = 初始x坐标 + 坦克矩形边的宽度 + tankBodyWidth / 2
         炮管的起始点y和圆盖的y坐标一致，startY = tankOvalY
         炮管的终点x和起点一致，endX = startX
         炮管的终点y等于炮管起点的y坐标减去其长度，y = tankOvalY - tankTubeLength;
         */

        /**
         向下炮管和向上炮管
         相同：起始点的x和终点的x相同
         区别：
         1、炮管向上 起始点y坐标等于坦克圆盖子y坐标，即： startY = tankOvalY;
         2、炮管向上 终点y坐标等于炮管起点的y坐标减去其长度，即：endY = tankOvalY - tankTubeLength;
         3、炮管向下 起始点y坐标等于坦克圆盖子y坐标 + 圆盖子宽度，即： startY = tankOvalY + tankOvalWidth;
         4、炮管向下 终点y坐标等于炮管起点的y坐标加上其长度，即：endY = tankOvalY + tankTubeLength;
         */
        int startX = x + tankRectWidth + tankBodyWidth / 2;
        if (direct == 0) {
            g.drawLine(startX, tankOvalY, startX, tankOvalY - tankTubeLength);
            return;
        }
        g.drawLine(startX, tankOvalY + tankOvalWidth, startX, tankOvalY + tankOvalWidth + tankTubeLength);
    }

    /**
     * 绘制向左或向右坦克
     *
     * @param g      画笔
     * @param direct 方向-> 2:向左,3向右
     */
    public void drawLeftRight(Graphics g, int direct) {

        /**
         * ▇▇▇▇▇▇
         *  █○█──
         * ▇▇▇▇▇▇
         *
         */
        // 绘制坦克上侧矩形
        g.fill3DRect(x, y, tankRectHeight, tankRectWidth, false);


        /**
         坦克的右侧矩形x坐标为：初始坐标
         坦克的右侧矩形y坐标：y + tankRectWidth + tankBodyWidth
         */
        // 绘制坦克下侧矩形
        g.fill3DRect(x, y + tankRectWidth + tankBodyWidth, tankRectHeight, tankRectWidth, false);

        /**
         坦克身体x轴坐标：初始坐标 + (坦克矩形边的高度 - 坦克身体的高度) / 2
         坦克身体y轴坐标：保证身体在中间位置，y = 初始坐标 + 坦克矩形边的宽度
         */
        // 绘制坦克身体
        int tankBodyX = x + (tankRectHeight - tankBodyHeight) / 2;
        g.fill3DRect(tankBodyX, y + tankRectWidth, tankBodyHeight, tankBodyWidth, false);

        /**
         保证坦克圆盖子在身体矩形中间位置，距离矩形身体(20*30) 上下各留距离5
         坦克圆盖子x轴坐标：和坦克身体X轴坐标一致
         坦克圆盖子y轴坐标：坦克身体y坐标 + (tankBodyHeight - tankOvalWidth) / 2
         */
        // 绘制坦克圆盖子
        int tankOvalX = tankBodyX + (tankBodyHeight - tankOvalWidth) / 2;
        int tankOvalY = y + tankRectWidth;
        g.fillOval(tankOvalX, tankOvalY, tankOvalWidth, tankOvalWidth);

        /**
         炮管的起始点x在坦克身体居中，startX = 圆盖子x + 炮管子长度
         炮管的起始点y和圆盖的y坐标等于圆盖子或身体的y轴坐标加圆宽度的一半，tankOvalY + tankBodyWidth / 2
         炮管的终点x等于炮管起点x+炮管子长度，startX + tankTubeLength
         炮管的终点y等于炮管起点y，因为炮管此时是水平线
         */
        // 绘制炮管
        int startX = tankOvalX + tankOvalWidth;
        int startY = tankOvalY + tankBodyWidth / 2;
        if (direct == 3) {
            g.drawLine(startX, startY, startX + tankTubeLength, startY);
            return;
        }
        // 向左炮管
        // 起始x和圆盖x一致，终点x=圆盖x-炮管长度
        g.drawLine(tankOvalX, startY, tankOvalX - tankTubeLength, startY);
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEnemyTankNum() {
        return enemyTankNum;
    }

    public void setEnemyTankNum(int enemyTankNum) {
        this.enemyTankNum = enemyTankNum;
    }


    public static int getTankX() {
        return tankX;
    }

    public static void setTankX(int tankX) {
        TankOld.tankX = tankX;
    }

    public static int getTankY() {
        return tankY;
    }

    public static void setTankY(int tankY) {
        TankOld.tankY = tankY;
    }

    public static int getEnemyTankX() {
        return enemyTankX;
    }

    public static void setEnemyTankX(int enemyTankX) {
        TankOld.enemyTankX = enemyTankX;
    }

    public static int getEnemyTankY() {
        return enemyTankY;
    }

    public static void setEnemyTankY(int enemyTankY) {
        TankOld.enemyTankY = enemyTankY;
    }

    public int getTankRectWidth() {
        return tankRectWidth;
    }

    public void setTankRectWidth(int tankRectWidth) {
        this.tankRectWidth = tankRectWidth;
    }

    public int getTankRectHeight() {
        return tankRectHeight;
    }

    public void setTankRectHeight(int tankRectHeight) {
        this.tankRectHeight = tankRectHeight;
    }

    public int getTankBodyWidth() {
        return tankBodyWidth;
    }

    public void setTankBodyWidth(int tankBodyWidth) {
        this.tankBodyWidth = tankBodyWidth;
    }

    public int getTankBodyHeight() {
        return tankBodyHeight;
    }

    public void setTankBodyHeight(int tankBodyHeight) {
        this.tankBodyHeight = tankBodyHeight;
    }

    public int getTankOvalWidth() {
        return tankOvalWidth;
    }

    public void setTankOvalWidth(int tankOvalWidth) {
        this.tankOvalWidth = tankOvalWidth;
    }

    public int getTankTubeLength() {
        return tankTubeLength;
    }

    public void setTankTubeLength(int tankTubeLength) {
        this.tankTubeLength = tankTubeLength;
    }
}
