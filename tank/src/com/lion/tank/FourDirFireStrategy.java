package com.lion.tank;

public class FourDirFireStrategy implements FireStrategy {

	@Override
	public void fire(Tank t) {
		int bx =t.getX(), by = t.getY();
		switch (t.getDir()) {
		case LEFT:
			bx = t.getX() - 10;
			by = t.getY() + t.WIDTH / 2 - 5;
			break;
		case UP:
			by = t.getY() - 10;
			bx = t.getX() + t.WIDTH / 2 - 5;
			break;
		case RIGHT:
			bx = t.getX() + t.HEIGHT;
			by = t.getY() + t.WIDTH / 2 - 5;
			break;
		case DOWN:
			bx = t.getX() + t.WIDTH / 2 - 5;
			by = t.getY() + t.HEIGHT;
			break;
		default:
			break;
		}
		Dir[] dirs=Dir.values();
		for(Dir dir:dirs)
				new Bullet(bx, by, dir,t.gettFrame(), t.getGroup());
	
	}

}
