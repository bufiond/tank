package com.lion.tank;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		TankFrame tFrame=new TankFrame();
		while (true) {
			Thread.sleep(50);
			tFrame.repaint();
			
		}
	}
}
