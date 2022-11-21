package com.zlm.tankgames;

/**
 * 子弹类 实现线程接口
 */
public class Shot implements Runnable {
    private int x;
    private int y;
    //方向(0:向上，1:向下，2:向左，3:向右)
    private int direct = 0;
    private int speed = 2;

    private Boolean isALive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("子弹x:" + x + "子弹y:" + y);
            if (direct == 0) {
                // 子弹向上
                y -= speed;
            } else if (direct == 1) {
                // 子弹向下
                y += speed;
            } else if (direct == 2) {
                // 子弹向左
                x -= speed;
            } else if (direct == 3) {
                // 子弹向右
                x += speed;
            }
            //System.out.println("子弹坐标x= " + x + " y= " + y);
            if (!(x > 0 && x < DrawTanksPanel.screenWidth && y > 0 && y < DrawTanksPanel.screenHeight && isALive)) {
                System.out.println("子弹停止...");
                isALive = false;
                break;
            }
            // 休眠200毫秒
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

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

    public Boolean getALive() {
        return isALive;
    }

    public void setALive(Boolean ALive) {
        isALive = ALive;
    }
}
