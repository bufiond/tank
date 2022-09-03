package test;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

public class ImageTest {
	@Test
	public void test() {
		try {
			BufferedImage image=ImageIO.read(new File("E:\\images\\tank.jpg"));
			//assertNotNull(image);
		BufferedImage image1=ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images\\tank.jpg"));
			assertNotNull(image1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	private void fail(String string) {
		System.out.println(string);
		
	}
}
