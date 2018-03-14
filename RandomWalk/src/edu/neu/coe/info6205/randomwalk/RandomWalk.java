package edu.neu.coe.info6205.randomwalk;

import java.util.Random;

public class RandomWalk {
    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    public void move(int dx, int dy) {
    	x += dx;
    	y += dy;
        // TODO you need to implement this
    }

    private void randomWalk(int n) {
        for (int i = 0; i < n; i++)
            randomMove();
    }

    private void randomMove() {
    	boolean randomX = random.nextBoolean();
    	boolean randomY = random.nextBoolean();
    	if(randomX) move(0,(randomY ? 1 : -1));
    	else move((randomY ? 1: -1), 0);
        // TODO you need to implement this
    }

    public double distance() {
        return Math.sqrt(x*x + y*y); // TODO you need to implement this
    }

    public static void main(String[] args) {
    	final int valueNum = 10;
    	final int repeat = 5;
    	int value = 1;
    	double sum = 0;
    	RandomWalk walk = new RandomWalk();
    	for(int j = 0; j < valueNum; j++) {
    		sum = 0;
    		for(int i = 0; i < repeat; i ++) {
    	        walk.x = walk.y = 0;
    	        walk.randomWalk(value);
    	        sum += walk.distance();
    		}
    		System.out.println("the avarage distance of "+ value + " steps: " + sum/repeat);
	        value += 10000;
		}
    }
}