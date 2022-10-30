package com.zlm.tankgames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class DrawTanksPanel extends JPanel implements KeyListener {

    public static int screenWidth = 800;
    public static int screenHeight = 800;
    //定义我方坦克
    Tank tank = null;
    //定义敌方坦克集合
    Vector<EnemyTank> tanks = null;

    public DrawTanksPanel() {
        // 初始化我方坦克对象
        tank = new Tank(Tank.tankX, Tank.tankY);
        tanks = new Vector<>();
        for (int i = 0; i < EnemyTank.enemyTankNum; i++) {
            EnemyTank enemyTank = new EnemyTank(EnemyTank.enemyX * (i + 1), EnemyTank.enemyY);
            // 设置炮管向下
            enemyTank.setDirect(1);
            //设置坦克类型
            enemyTank.setType(1);
            tanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //设置背景色
        g.setColor(Color.gray);
        // 填充背景色
        g.fillRect(0, 0, screenWidth, screenHeight);
        // 绘制我方坦克
        drawTank(g, tank);
        // 绘制敌方坦克
        for (int i = 0; i < EnemyTank.enemyTankNum; i++) {
            drawTank(g, tanks.get(i));
        }
    }

    /**
     * @param g 画笔
     *          tankType 坦克类型(0:我方坦克，1:敌方坦克)
     *          direct   坦克方向(0:向上，1:向下，2:向左，3:向右)
     */
    public void drawTank(Graphics g, Tank tank) {
        switch (tank.getType()) {
            //创建我方坦克
            case 0:
                g.setColor(Color.yellow);
                break;
            // 创建敌方坦克
            case 1:
                g.setColor(Color.cyan);
                break;
        }

        switch (tank.getDirect()) {
            //坦克方向：向上或下
            case 0:
            case 1:
                drawUpDown(g, tank);
                break;
            // 坦克方向：向左或右
            case 2:
            case 3:
                drawLeftRight(g, tank);
                break;
        }
    }

    /**
     * 绘制向上或向下坦克
     *
     * @param g 画笔
     *          direct 方向-> 0:向上，1：向下
     */
    public void drawUpDown(Graphics g, Tank tank) {
        /**
         ▊  ▏ ▊
         ▊▇○▇▉
         ▊    ▊
         */
        // 绘制坦克左侧矩形
        g.fill3DRect(tank.getX(), tank.getY(), tank.getRectWidth(), tank.getRectHeight(), false);

        /**
         坦克的右侧矩形x坐标为：初始坐标 + 坦克矩形边的宽度 +  坦克身体宽度
         坦克的右侧矩形y坐标和左侧相同，即初始y坐标
         */
        // 绘制坦克右侧矩形
        g.fill3DRect(tank.getX() + tank.getRectWidth() + tank.getBodyWidth(), tank.getY(), tank.getRectWidth(), tank.getRectHeight(), false);

        /**
         坦克身体x轴坐标：初始坐标 + 坦克矩形边的宽度
         坦克身体y轴坐标：保证身体在中间位置，y = 初始坐标 + (坦克矩形边的高度 - 坦克身体的高度) / 2
         */
        // 绘制坦克身体
        int tankBodyY = tank.getY() + (tank.getRectHeight() - tank.getBodyHeight()) / 2;
        g.fill3DRect(tank.getX() + tank.getRectWidth(), tankBodyY, tank.getBodyWidth(), tank.getBodyHeight(), false);

        /**
         保证坦克圆盖子在身体矩形中间位置，距离矩形身体(20*30) 上下各留距离5
         坦克圆盖子x轴坐标：初始x坐标 + 坦克矩形边的宽度
         坦克圆盖子y轴坐标：坦克身体坐标 + (tankBodyHeight - tankOvalWidth) / 2
         */
        // 绘制坦克圆盖子
        int tankOvalY = tankBodyY + (tank.getBodyHeight() - tank.getOvalWidth()) / 2;
        g.fillOval(tank.getX() + tank.getRectWidth(), tankOvalY, tank.getOvalWidth(), tank.getOvalWidth());


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
        int startX = tank.getX() + tank.getRectWidth() + tank.getBodyWidth() / 2;
        if (tank.getDirect() == 0) {
            g.drawLine(startX, tankOvalY, startX, tankOvalY - tank.getTubeLength());
            return;
        }
        g.drawLine(startX, tankOvalY + tank.getOvalWidth(), startX, tankOvalY + tank.getOvalWidth() + tank.getTubeLength());
    }

    /**
     * 绘制向左或向右坦克
     *
     * @param g 画笔
     *          direct 方向-> 2:向左,3向右
     */
    public void drawLeftRight(Graphics g, Tank tank) {

        /**
         * ▇▇▇▇▇▇
         *  █○█──
         * ▇▇▇▇▇▇
         *
         */
        // 绘制坦克上侧矩形
        g.fill3DRect(tank.getX(), tank.getY(), tank.getRectHeight(), tank.getRectWidth(), false);


        /**
         坦克的右侧矩形x坐标为：初始坐标
         坦克的右侧矩形y坐标：y + tankRectWidth + tankBodyWidth
         */
        // 绘制坦克下侧矩形
        g.fill3DRect(tank.getX(), tank.getY() + tank.getRectWidth() + tank.getBodyWidth(), tank.getRectHeight(), tank.getRectWidth(), false);

        /**
         坦克身体x轴坐标：初始坐标 + (坦克矩形边的高度 - 坦克身体的高度) / 2
         坦克身体y轴坐标：保证身体在中间位置，y = 初始坐标 + 坦克矩形边的宽度
         */
        // 绘制坦克身体
        int tankBodyX = tank.getX() + (tank.getRectHeight() - tank.getBodyHeight()) / 2;
        g.fill3DRect(tankBodyX, tank.getY() + tank.getRectWidth(), tank.getBodyHeight(), tank.getBodyWidth(), false);

        /**
         保证坦克圆盖子在身体矩形中间位置，距离矩形身体(20*30) 上下各留距离5
         坦克圆盖子x轴坐标：和坦克身体X轴坐标一致
         坦克圆盖子y轴坐标：坦克身体y坐标 + (tankBodyHeight - tankOvalWidth) / 2
         */
        // 绘制坦克圆盖子
        int tankOvalX = tankBodyX + (tank.getBodyHeight() - tank.getOvalWidth()) / 2;
        int tankOvalY = tank.getY() + tank.getRectWidth();
        g.fillOval(tankOvalX, tankOvalY, tank.getOvalWidth(), tank.getOvalWidth());

        /**
         炮管的起始点x在坦克身体居中，startX = 圆盖子x + 炮管子长度
         炮管的起始点y和圆盖的y坐标等于圆盖子或身体的y轴坐标加圆宽度的一半，tankOvalY + tankBodyWidth / 2
         炮管的终点x等于炮管起点x+炮管子长度，startX + tankTubeLength
         炮管的终点y等于炮管起点y，因为炮管此时是水平线
         */
        // 绘制炮管
        int startX = tankOvalX + tank.getOvalWidth();
        int startY = tankOvalY + tank.getBodyWidth() / 2;
        if (tank.getDirect() == 3) {
            g.drawLine(startX, startY, startX + tank.getTubeLength(), startY);
            return;
        }
        // 向左炮管
        // 起始x和圆盖x一致，终点x=圆盖x-炮管长度
        g.drawLine(tankOvalX, startY, tankOvalX - tank.getTubeLength(), startY);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            tank.setDirect(0);
            tank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            tank.setDirect(1);
            tank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            tank.setDirect(2);
            tank.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            tank.setDirect(3);
            tank.moveRight();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
