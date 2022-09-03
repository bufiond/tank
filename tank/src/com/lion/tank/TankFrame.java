package com.lion.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tank myTank=new Tank(260,500,Dir.UP,this,Group.GOOD);
	List<Bullet> bullets=new ArrayList<>();
	List<Tank> foeTanks=new ArrayList<>();
	Random random=new Random();
	
	List<Explode> explodes=new ArrayList<>();
	
	//Bullet bullet=new Bullet(100, 100, Dir.RIGHT);
	private static final int GAME_WIDTH=800;
	private static final int GAME_HEIGHT=600;

	public static int getGameWidth() {
		return GAME_WIDTH;
	}
	public static int getGameHeight() {
		return GAME_HEIGHT;
	}

	Image offScreenImage=null;
	
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setTitle("TANK WAR");
		setVisible(true);
		//监听键盘动作
		this.addKeyListener(new MyKeyListener());
		//关闭窗口
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				super.windowClosing(e);
			}
		});
		for(int i=1;i<random.nextInt(20);i++) {
			
			foeTanks.add(new Tank(100*i,100,randomDir(),this,Group.BAD));
		}
		
	}
	@Override
	//消除闪屏 ,原理 在内存中开辟一块空间，缓存paint ,然后从内存中一次刷新到屏幕一整屏内容
	public void update(Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen= offScreenImage.getGraphics();
		Color color=gOffScreen.getColor();
		gOffScreen.setColor(Color.black);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(color);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	@Override
	public void paint(Graphics g) {
		
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.drawRect(50, 50, 240, 30);
		g.drawString("Bullet count:  "+ bullets.size(), 60, 70);
		g.drawString("Enemy count:  "+ foeTanks.size(), 160, 70);
		
		g.setColor(c);
		
		myTank.paint(g);//我方坦克
		//敌方坦克
		for(int i=0;i<foeTanks.size();i++)
			foeTanks.get(i).paint(g);
		//敌方坦克开火
		for(int i=0;i<foeTanks.size();i++)
			if(random.nextInt(20)>18)
				foeTanks.get(i).fire();
		//子弹生成
		for(int i=0;i<bullets.size();i++)
		    bullets.get(i).paint(g);
		//删除子弹的另一种方法
//		for(Bullet b:bullets) b.paint(g);
//		for(Iterator<Bullet> it=bullets.iterator();it.hasNext();) {
//			Bullet bullet=it.next();
//			if(!bullet.live) it.remove();
//		}
		
		//碰撞检测我方子弹和敌方坦克
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < foeTanks.size(); j++) {
				
				bullets.get(i).collideWith(foeTanks.get(j));
			}
		}
		//碰撞检测敌方坦克和敌方坦克
		for (int i = 0; i < foeTanks.size(); i++) {
			for (int j = 0; j < foeTanks.size(); j++) {
				if(i==j)continue;
				try {
					foeTanks.get(i).collideWith(foeTanks.get(j));
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		//画爆炸效果
		for(int i=0;i<explodes.size();i++) {
			explodes.get(i).paint(g);		
		}
			
		
		
	}
	public Dir randomDir() {
		int d=random.nextInt(4);
		Dir tempDir=Dir.DOWN;
		switch(d) {
		case 0:
			tempDir=Dir.UP;
			break;
		case 1:
			tempDir=Dir.DOWN;
			break;
		case 2:
			tempDir=Dir.RIGHT;
			break;
		case 3:
			tempDir=Dir.LEFT;
			break;
		}
		return tempDir;
	}
	
	class MyKeyListener extends KeyAdapter{
		//获取按键状态，可以是组合形式
		boolean bL =false;
		boolean bR =false;
		boolean bU =false;
		boolean bD =false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key=e.getKeyCode();			
			switch (key) {
				case KeyEvent.VK_LEFT:
					bL=true;
				break;
				case KeyEvent.VK_UP:
					bU=true;
				break;
				case KeyEvent.VK_RIGHT:
					bR=true;
				break;
				case KeyEvent.VK_DOWN:
					bD=true;
				break;
				case KeyEvent.VK_SPACE:
					//new Audio("audio\\kqs.mp3").run();
				break;
				default:
					break;
			}	
			setMainTankerDir();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key=e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT:
					bL=false;
				break;
				case KeyEvent.VK_UP:
					bU=false;
				break;
				case KeyEvent.VK_RIGHT:
					bR=false;
				break;
				case KeyEvent.VK_DOWN:
					bD=false;
				break;
				case KeyEvent.VK_CONTROL:
					myTank.fire();
				break;
				default:
					break;
			}
			setMainTankerDir();
		}
		
		private void setMainTankerDir() {
			if(!bL&&!bU&&!bR&&!bD)
				myTank.setMoving(false);
			else {
				myTank.setMoving(true);
				
				if(bL) myTank.setDir(Dir.LEFT);;
				if(bU) myTank.setDir(Dir.UP);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bD) myTank.setDir(Dir.DOWN);
			}
		}
		
	}
}
