package com.zlm.draw;

import javax.swing.*;
import java.awt.*;

public class Tanks extends JFrame {
    private TanksPanel tp = null;

    public static void main(String[] args) {
        new Tanks();
    }

    public Tanks() {
        tp = new TanksPanel();
        this.add(tp);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}

class TanksPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 设置填充背景色
        g.setColor(Color.gray);
        g.fillRect(0, 0, 500, 500);
        //设置圆的位置
        g.drawOval(100, 100, 50, 50);
        g.setColor(Color.blue);
        g.fillOval(100, 100, 50, 50);

        // 设置炮管的位置
        g.drawLine(125, 100, 125, 50);

        //设置身体
        g.drawRect(100, 80, 50, 90);

        //绘制左侧矩形
        g.drawRect(80, 50, 20, 150);
        //绘制右侧矩形
        g.drawRect(150, 50, 20, 150);
    }
}

//1.画直线drawLine(int x1,int y1,int x2,int y2)
//2.画矩形边框drawRect(int x, int y, int width, int height)
//3.画椭圆边框drawOval(int x, int y, int width, int height)
//4.填充矩形 fillRect(int x, int y, int width, int height)
//5.填充椭圆fillOval(int x, int y, int width, int height)
//6.画图片drawlmage(lmage img, int x, int y, ..)
//7.画字符串drawString(String str,int x, int y)
//8.设置画笔的字体setFont(Font font)
//9.设置画笔的颜色setColor(Color c)
