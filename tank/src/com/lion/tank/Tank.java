package com.lion.tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import org.junit.jupiter.params.provider.NullEnum;

public class Tank {
	private int x, y;

	private Dir dir = Dir.RIGHT;
	private static final int SPEED = 3;
	private boolean moving = true;
	private boolean living = true;
	private TankFrame tFrame;
	private Group group = Group.GOOD;
	Random random = new Random();
	public int WIDTH = ResourceMgr.tankD.getWidth();
	public int HEIGHT = ResourceMgr.tankD.getHeight();

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Tank(int x, int y, Dir dir, TankFrame tf, Group group) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tFrame = tf;
		this.group = group;
	}

	public void paint(Graphics g) {
		if (!living)
			tFrame.foeTanks.remove(this);
		if (this.group == Group.GOOD) {
			switch (dir) {
			case LEFT:
				g.drawImage(ResourceMgr.mytankL, x, y, null);
				break;
			case UP:
				g.drawImage(ResourceMgr.mytankU, x, y, null);
				break;
			case RIGHT:
				g.drawImage(ResourceMgr.mytankR, x, y, null);
				break;
			case DOWN:
				g.drawImage(ResourceMgr.mytankD, x, y, null);
				break;

			default:
				break;
			}
		} else {

			switch (dir) {
			case LEFT:
				g.drawImage(ResourceMgr.tankL, x, y, null);
				break;
			case UP:
				g.drawImage(ResourceMgr.tankU, x, y, null);
				break;
			case RIGHT:
				g.drawImage(ResourceMgr.tankR, x, y, null);
				break;
			case DOWN:
				g.drawImage(ResourceMgr.tankD, x, y, null);
				break;

			default:
				break;
			}
		}
		move();

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

	private void move() {
		if (!moving)
			return;
		if (x <= 0) {
			x = 10;
			dir = randomDir();
		}
		if (x >= tFrame.getWidth() - this.WIDTH) {
			x = tFrame.getWidth() - this.WIDTH - 10;
			dir = randomDir();
		}
		if (y <= 0) {
			y = this.WIDTH - 10;
			dir = randomDir();
		}
		if (y >= tFrame.getHeight() - this.HEIGHT) {
			y = tFrame.getHeight() - this.HEIGHT - 10;
			dir = randomDir();
		}
		switch (dir) {
		case LEFT:
			x -= SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		default:
			break;
		}
		
	}

	public void fire() {
		int bx = x, by = y;
		switch (dir) {
		case LEFT:
			bx = x - 10;
			by = y + WIDTH / 2 - 5;
			break;
		case UP:
			by = y - 10;
			bx = x + WIDTH / 2 - 5;
			break;
		case RIGHT:
			bx = x + HEIGHT;
			by = y + WIDTH / 2 - 5;
			break;
		case DOWN:
			bx = x + WIDTH / 2 - 5;
			by = y + HEIGHT;
			break;
		default:
			break;
		}
		tFrame.bullets.add(new Bullet(bx, by, this.dir, tFrame, this.group));

	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void die() {
		this.living = false;
	}

	public Dir randomDir() {
		int d = random.nextInt(4);
		Dir tempDir = Dir.DOWN;
		switch (d) {
		case 0:
			tempDir = Dir.UP;
			break;
		case 1:
			tempDir = Dir.DOWN;
			break;
		case 2:
			tempDir = Dir.RIGHT;
			break;
		case 3:
			tempDir = Dir.LEFT;
			break;
		}
		return tempDir;
	}
	public void collideWith(Tank tank) throws Exception {
		
		Rectangle mtrect=new Rectangle(this.x,this.y,WIDTH,HEIGHT);
		Rectangle trect=new Rectangle(tank.getX(),tank.getY(),tank.WIDTH,tank.HEIGHT);
		if(mtrect.intersects(trect)) {
			if(this.group==tank.getGroup()) {
				this.dir=tank.getDir();
				tank.dir=randomDir();
			}
			else {
				tFrame.explodes.add(new  Explode(this.x, this.y, tFrame));
				tank.die();
			}		
		}
	}
}
