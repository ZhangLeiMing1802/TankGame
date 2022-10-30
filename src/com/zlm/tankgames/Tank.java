package com.zlm.tankgames;

public class Tank {
    // x坐标
    protected int x;
    // y坐标
    protected int y;

    // 坦克方向
    protected int direct;
    // 坦克移动速度
    protected int speed = 3;

    // 坦克类型，0：我方，1：敌方
    protected int type = 0;

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

    public static int getTankX() {
        return tankX;
    }

    public static void setTankX(int tankX) {
        Tank.tankX = tankX;
    }

    public static int getTankY() {
        return tankY;
    }

    public static void setTankY(int tankY) {
        Tank.tankY = tankY;
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
}
