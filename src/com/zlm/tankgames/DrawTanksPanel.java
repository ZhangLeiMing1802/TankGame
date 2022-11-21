package com.zlm.tankgames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

@SuppressWarnings("all")
public class DrawTanksPanel extends JPanel implements KeyListener, Runnable {

    public static int screenWidth = 800;
    public static int screenHeight = 800;
    //定义我方坦克
    Hero hero = null;
    //定义敌方坦克集合
    Vector<EnemyTank> enemyTanks = null;

    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    Vector<Bobm> bobms = new Vector<>();

    public DrawTanksPanel() {
        // 初始化我方坦克对象
        hero = new Hero(Tank.tankX, Tank.tankY);
        enemyTanks = new Vector<>();
        System.out.println("请输入你的选择，1：新游戏，2：继续游戏");
        Scanner scanner = new Scanner(System.in);
        int type = scanner.nextInt();
        switch (type) {
            case 1:
                for (int i = 0; i < EnemyTank.enemyTankNum; i++) {
                    EnemyTank enemyTank = new EnemyTank(EnemyTank.enemyX * (i + 1), EnemyTank.enemyY);
                    // 设置炮管向下
                    enemyTank.setDirect(1);
                    //设置坦克类型
                    enemyTank.setType(1);
                    Thread thread = new Thread(enemyTank);
                    thread.start();
                    System.out.println("敌方坦克初始位置x:" + enemyTank.getX() + ",y:" + enemyTank.getY());
                    enemyTanks.add(enemyTank);
                }
                break;
            case 2:
                Vector<Node> nodes = new Vector<>();
                BufferedReader reader = null;
                // 取出数据
                try {
                    reader = new BufferedReader(new FileReader(Record.src));
                    int hitNum = Integer.parseInt(reader.readLine());
                    Record.setScore(hitNum);
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] s = line.split(" ");
                        Node n = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                        nodes.add(n);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                for (int i = 0; i < nodes.size(); i++) {
                    EnemyTank enemyTank = new EnemyTank(nodes.get(i).getX() * (i + 1), nodes.get(i).getY());
                    // 设置炮管向下
                    enemyTank.setDirect(nodes.get(i).getDirect());
                    //设置坦克类型
                    enemyTank.setType(1);
                    Thread thread = new Thread(enemyTank);
                    thread.start();
                    System.out.println("敌方坦克初始位置x:" + enemyTank.getX() + ",y:" + enemyTank.getY());
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");
        }

        image1 = Toolkit.getDefaultToolkit().getImage(DrawTanksPanel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(DrawTanksPanel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(DrawTanksPanel.class.getResource("/bomb_3.gif"));

        //加载音乐
        AePlayWave aePlayWave = new AePlayWave("src\\111.wav");
        aePlayWave.start();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //设置背景色
        g.setColor(Color.gray);
        // 填充背景色
        g.fillRect(0, 0, screenWidth, screenHeight);
        // 绘制我方坦克
        drawTank(g, hero);
        // 绘制敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            if (enemyTanks.get(i).isLive) {
                // 绘制坦克
                drawTank(g, enemyTanks.get(i));
                Vector<Shot> shots = enemyTanks.get(i).shots;
                for (int j = 0; j < shots.size(); j++) {
                    Shot shot = shots.get(j);
                    if (shot != null && shot.getALive()) {
                        // 绘制子弹
                        drawShot(g, shots.get(j), enemyTanks.get(i));
                    } else {
                        shots.remove(shot);
                    }
                }
            }
        }
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.getALive()) {
                // 绘制子弹
                drawShot(g, shot, hero);
            } else {
                System.out.println("移除子弹");
                hero.shots.remove(shot);
            }
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //绘制爆炸
        for (int i = 0; i < bobms.size(); i++) {
            Bobm bobm = bobms.get(i);
            if (bobm.life > 6) {
                g.drawImage(image3, bobm.x, bobm.y, this);
            } else if (bobm.life > 3 && bobm.life < 6) {
                g.drawImage(image2, bobm.x, bobm.y, this);
            } else if (bobm.life > 0 && bobm.life < 3) {
                g.drawImage(image1, bobm.x, bobm.y, this);
            } else {
                bobms.remove(bobm);
            }
            bobm.downLife();
            if (bobm.life == 0) {
                bobms.remove(bobm);
            }
        }

        // 绘制右侧得分区域
        drawScore(g);

    }

    public void drawScore(Graphics g) {
        // 文字
        g.setColor(Color.black);
        g.setFont(new Font("宋体", 0, 20));
        g.drawString("您累计击毁敌方坦克", 820, 50);
        // 坦克
        EnemyTank enemyTank = new EnemyTank(820, 80);
        enemyTank.setType(1);
        enemyTank.setDirect(0);
        drawTank(g, enemyTank);
        // 记录
        g.setColor(Color.black);
        g.drawString(Record.getScore() + "", 870, 120);
        g.setFont(new Font("宋体", 0, 30));
    }

    public void drawShot(Graphics g, Shot shot, Tank tank) {
        g.setColor(tank.getColor());
        g.fillOval(shot.getX(), shot.getY(), 4, 4);
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
                tank.setColor(Color.yellow);
                break;
            // 创建敌方坦克
            case 1:
                g.setColor(Color.cyan);
                tank.setColor(Color.cyan);
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
            //记录炮管位置
            tank.setTubeEndX(startX);
            tank.setTubeEndY(tankOvalY - tank.getTubeLength());
            return;
        }
        g.drawLine(startX, tankOvalY + tank.getOvalWidth(), startX, tankOvalY + tank.getOvalWidth() + tank.getTubeLength());
        //记录炮管位置
        tank.setTubeEndX(startX);
        tank.setTubeEndY(tankOvalY + tank.getOvalWidth() + tank.getTubeLength());
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
            //记录炮管位置
            tank.setTubeEndX(startX + tank.getTubeLength());
            tank.setTubeEndY(startY);
            g.drawLine(startX, startY, startX + tank.getTubeLength(), startY);
            return;
        }
        // 向左炮管
        // 起始x和圆盖x一致，终点x=圆盖x-炮管长度
        g.drawLine(tankOvalX, startY, tankOvalX - tank.getTubeLength(), startY);
        //记录炮管位置
        tank.setTubeEndX(tankOvalX - tank.getTubeLength());
        tank.setTubeEndY(startY);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(1);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(2);
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(3);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                // 先射击后移除  否则空指针
                hitEnemyTank();
                this.repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void hitTank(Shot shot, Tank tank) {
        int startX = 0, endX = 0, startY = 0, endY = 0;
        switch (tank.getDirect()) {
            case 0:
            case 1:
                startX = tank.getX();
                endX = tank.getX() + tank.getBodyWidth() * 2 + tank.getBodyWidth();
                startY = tank.getY();
                endY = tank.getY() + tank.getRectHeight();
                break;
            case 2:
            case 3:
                startX = tank.getX();
                endX = tank.getX() + tank.getRectHeight();
                startY = tank.getY();
                endY = tank.getY() + tank.getBodyWidth() * 2 + tank.getBodyWidth();
                break;
        }
        if ((shot.getX() > startX && shot.getX() < endX) && (shot.getY() > startY && shot.getY() < endY)) {
            // 击中敌方
            tank.isLive = false;
            shot.setALive(false);
            enemyTanks.remove(tank);
            System.out.println("图片位置:" + tank.getX() + " , " + tank.getY());
            bobms.add(new Bobm(tank.getX(), tank.getY()));
            // 增加击毁次数
            Record.addNum();
        }
    }

    public void hitEnemyTank() {
        //子弹落在坦克的范围内 则击败
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.getALive()) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    hitTank(hero.shots.get(i), enemyTanks.get(j));
                }
            }
        }
    }

}
