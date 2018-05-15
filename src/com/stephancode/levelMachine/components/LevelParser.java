package com.stephancode.levelMachine.components;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stephancode.entities.Blinky;
import com.stephancode.entities.food.Food;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.levelMachine.components.tiles.Tile;

public class LevelParser {

	private int[] pixels;
	private int[] tiles;
	private BufferedImage img;
	
	public LevelParser(LevelComponent level, String path){
		try {
			img = ImageIO.read(LevelParser.class.getResourceAsStream(path));
			
			pixels = new int[img.getWidth() * img.getHeight()];
			tiles = new int[img.getWidth() * img.getHeight()];
			
			int count = 0;
			for(int y = 0; y < level.h; y++){
				for(int x = 0; x < level.w; x++){
					int color = img.getRGB(x, y);
					
					if(color == 0xff000000) tiles[x + y * img.getWidth()] = Tile.wall.id;
					else if(color == 0xffffffff) {
						tiles[x + y * img.getWidth()] = Tile.air.id;
						level.addEntity(new Food(x, y, 1));
						++count;
					} else if(color == 0xffffffee) {
						tiles[x + y * img.getWidth()] = Tile.air.id;
					} else if(color == 0xffff0000) {
						level.addEntity(new Blinky(x, y));
						tiles[x + y * img.getWidth()] = Tile.air.id;
					}
				}
			}
			
			
			if(LevelManager.foodCount == 0){
				LevelManager.foodCount = count;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getTiles(){ return tiles; }
	
}
