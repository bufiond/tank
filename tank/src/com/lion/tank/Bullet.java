package com.lion.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
	private int x, y;
	boolean living=true;
	private Dir dir = Dir.RIGHT;
	private Group group = Group.GOOD;
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	private static final int SPEED = 10;
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	private TankFrame tFrame;
	public  Bullet(int x,int y,Dir dir,TankFrame tf,Group group) {
		this.x=x;
		this.y=y;
		this.dir=dir;
		this.tFrame=tf;
		this.group=group;
	}
	public void paint(Graphics g) {
		if(!living) {
			tFrame.bullets.remove(this);
		}
		Color color=g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(color);
		
		move();
	}
	
	private void move() {
		// TODO 自动生成的方法存根
		switch (dir) {
		case LEFT:
			x-=SPEED;
			break;
		case UP:
			y-=SPEED;
			break;
		case RIGHT:
			x+=SPEED;
			break;
		case DOWN:
			y+=SPEED;
			break;
		default:
			break;
		}
		if(x<0||y<0||x>TankFrame.getGameWidth()||y>TankFrame.getGameHeight())living=false;
	}
	public void collideWith(Tank tank) {
		Rectangle brect=new Rectangle(this.x,this.y,WIDTH,HEIGHT);
		Rectangle trect=new Rectangle(tank.getX(),tank.getY(),tank.WIDTH,tank.HEIGHT);
		if(brect.intersects(trect)) {
			if(this.group==tank.getGroup())return;
			else {
				tFrame.explodes.add(new  Explode(this.x, this.y, tFrame));
				tank.die();
				this.die();
			}
			
		}
	}
	private void die() {
		this.living=false;
		
	}
}
