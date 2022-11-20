package ui;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.Texture;

public class NumbersTexture {
	public static Texture[] numbersTextures = new Texture[10];
	
	public static void load() {
		try {
			BufferedImage img = ImageIO.read(new FileInputStream("res/numbers.png"));
			
			for(int i = 0; i < 10; ++i) {
			numbersTextures[i] = new Texture(img.getSubimage(i * 20, 0, 20, img.getHeight()));
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
