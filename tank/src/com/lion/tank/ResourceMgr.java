package com.lion.tank;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ResourceMgr {
	public static BufferedImage tankL, tankR, tankU, tankD;
	public static BufferedImage mytankL, mytankR, mytankU, mytankD;
	public static BufferedImage[] explodes=new BufferedImage[9];
	static {
		try {
			tankL=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images\\tank4.png"));
			tankR=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images\\tank2.png"));
			tankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images\\tank1.png"));
			tankD=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images\\tank3.png"));
			
			mytankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images\\mytank1.png"));
			mytankR=ImageUtil.rotateImage(mytankU,90);
			mytankL=ImageUtil.rotateImage(mytankU,-90);
			mytankD=ImageUtil.rotateImage(mytankU,180);
			
			for (int i = 1; i < 9; i++) {
				explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images\\b"+i+".png"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
