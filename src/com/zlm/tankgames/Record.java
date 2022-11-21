package com.zlm.tankgames;

import java.io.*;
import java.util.Vector;

public class Record {
     static String src = "src\\record.txt";
    private static int score;

    public static void addNum() {
        score++;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Record.score = score;
    }

    /**
     * 把得分保存在文件中
     */
    public static void keepRecord(Vector<EnemyTank> enemyTanks) {
        BufferedWriter bufferedWriter = null;
        try {
            // 写入得分
            bufferedWriter = new BufferedWriter(new FileWriter(src));
            bufferedWriter.write(score + "\r\n");
            //写入坦克坐标和方向
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                bufferedWriter.write(enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect() + "\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
