package com.stephancode.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {

	public static Art tileset = new Art("/tileset.png");
	public static Art font = new Art("/font.png");
	
	BufferedImage image;
	public int w = 0;
	public int h = 0;
	public int pixels[];
	
	public Art(String path){
		try {
			image = ImageIO.read(Art.class.getResourceAsStream(path));
			w = image.getWidth();
			h = image.getHeight();
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
