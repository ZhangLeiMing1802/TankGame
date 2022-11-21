package com.zlm.tankgames;

public class Bobm {
    public int x, y;
    public int life = 9;
    public boolean isLive = true;

    public Bobm(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void downLife() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
