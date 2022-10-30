package com.zlm.threadex;

public class ThreadMethodExercise {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            System.out.println("hi " + i);
            if (i == 5) {
                Thread thread = new Thread(t);
                thread.start();
                thread.join();
            }
        }
    }
}

class T implements Runnable {
    int count = 0;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("子线程 hello " + (++count));
            if (count == 10) {
                break;
            }
        }
    }
}
