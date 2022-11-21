package com.zlm.tankgames;

import java.awt.*;
import java.util.Vector;

public class Tank {
    // x坐标
    protected int x;
    // y坐标
    protected int y;

    /**
     * direct   坦克方向(0:向上，1:向下，2:向左，3:向右)
     */
    protected int direct;
    // 坦克移动速度
    protected int speed = 5;

    // 坦克类型，0：我方，1：敌方
    protected int type = 0;

    // 记录当前炮管的位置
    protected int tubeEndX = 0;
    protected int tubeEndY = 0;

    //定义子弹(原来定义的子弹是一个对象，在发射第二个子弹的时候，第一个子弹会被回收-重新new了，所以需要定义集合存取)
    //protected Shot shot = null;
    // 是否存活
    boolean isLive = true;

    protected Color color = null;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //坦克矩形边的x轴坐标(作为绘制坦克的参照点)
    public static int tankX = 100;
    //坦克矩形边的y轴坐标(作为绘制坦克的参照点)
    public static int tankY = 100;
    //坦克的矩形边的宽度
    protected int rectWidth = 10;
    //坦克的矩形边的高度
    protected int rectHeight = 60;
    //坦克身体宽度
    protected int bodyWidth = 20;
    //坦克身体高度
    protected int bodyHeight = 30;
    // 坦克圆盖子大小
    protected int ovalWidth = 20;
    // 坦克炮管长度
    protected int tubeLength = 20;

    public void moveUp() {
        y = y > 0 ? y - speed : y;
    }

    public void moveDown() {
        y = y + rectHeight < DrawTanksPanel.screenWidth ? y + speed : y;
    }

    public void moveLeft() {
        x = x > 0 ? x - speed : x;
    }

    public void moveRight() {
        x = x + rectHeight < DrawTanksPanel.screenWidth ? x + speed : x;
    }

    /**
     * 根据当前坦克方向，创建shot对象
     * 判断坦克方向(0:向上，1:向下，2:向左，3:向右)
     * 按照坦克左上角为参照，计算炮管末端坐标，即为子弹坐标
     *
     * @return
     */
    public Shot initShot() {
        int shotX = 0;
        int shotY = 0;
        Shot shot = null;
        System.out.println("创建子弹...");
        switch (getDirect()) {
            case 0:
                // 坦克初始x坐标 + (坦克矩形边的宽度 - 坦克身体宽度 ) /2
                shotX = getX() + getRectWidth() + getBodyWidth() / 2;
                // 坦克初始y坐标 + (坦克矩形边的高度 - 坦克身体高度 ) /2   得到坦克炮管初始位置
                // 坦克炮管初始位置 - 坦克炮管长度  得到坦克炮管终点位置坐标
                shotY = getY() + (getRectHeight() - getBodyHeight()) / 2 - getTubeLength();
                break;
            case 1:
                // x坐标和向上保存一致
                shotX = getX() + getRectWidth() + getBodyWidth() / 2;
                // 坦克初始y坐标 + (坦克矩形边的高度 - 坦克身体高度 ) /2 + 坦克身体高度   得到坦克炮管初始位置
                // 坦克炮管初始位置 + 坦克炮管长度  得到坦克炮管终点位置坐标
                shotY = getY() + (getRectHeight() - getBodyHeight()) / 2 + getBodyHeight() + getTubeLength();
                break;
            case 2:
                // 坦克初始x坐标 + 坦克矩形边的高度 - 坦克身体高度 ) /2 - 坦克炮管长度
                shotX = getX() + (getRectHeight() - getBodyHeight()) / 2 - getTubeLength();
                // 坦克初始y坐标 + 坦克矩形边的宽度 + 坦克身体宽度  /2
                shotY = getY() + getRectWidth() + getBodyWidth() / 2;
                break;
            case 3:
                // 坦克初始x坐标 + (坦克矩形边的高度 - 坦克身体高度 ) /2 + 坦克身体宽度  + 坦克炮管长度
                shotX = getX() + (getRectHeight() - getBodyHeight()) / 2 + getBodyWidth() + getTubeLength();
                // 坦克初始y坐标 + 坦克矩形边的宽度 + 坦克身体宽度  /2
                shotY = getY() + getRectWidth() + getBodyWidth() / 2;
                break;
        }
        return new Shot(shotX, shotY, getDirect());
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

    public static int getTankX() {
        return tankX;
    }

    public static void setTankX(int tankX) {
        tankX = tankX;
    }

    public static int getTankY() {
        return tankY;
    }

    public static void setTankY(int tankY) {
        tankY = tankY;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth) {
        this.rectWidth = rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public void setRectHeight(int rectHeight) {
        this.rectHeight = rectHeight;
    }

    public int getBodyWidth() {
        return bodyWidth;
    }

    public void setBodyWidth(int bodyWidth) {
        this.bodyWidth = bodyWidth;
    }

    public int getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(int bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public int getOvalWidth() {
        return ovalWidth;
    }

    public void setOvalWidth(int ovalWidth) {
        this.ovalWidth = ovalWidth;
    }

    public int getTubeLength() {
        return tubeLength;
    }

    public void setTubeLength(int tubeLength) {
        this.tubeLength = tubeLength;
    }

    public int getTubeEndX() {
        return tubeEndX;
    }

    public void setTubeEndX(int tubeEndX) {
        this.tubeEndX = tubeEndX;
    }

    public int getTubeEndY() {
        return tubeEndY;
    }

    public void setTubeEndY(int tubeEndY) {
        this.tubeEndY = tubeEndY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
