package com.stephancode.entities;

import java.util.Random;

import com.stephancode.graphics.Font;
import com.stephancode.graphics.Screen;
import com.stephancode.main.KeyHandler;

public class MPlayer extends Entity{
	
	int tiles[] = {0 + 1 * 8, 1 + 1 * 8, 2 + 1 * 8}; 
	int vfactor = 0;
	byte mirror = 0x00;
	int time = 0;

	private int sx; 
	private int sy; 
	
	public MPlayer(String username){
		this.w = 16;
		this.h = 16;
		this.x = 16;
		this.y = 16;
		this.dir = -1;
		this.username = username;
		sx = x;
		sy = y;
	}
	
	// @CleanUp
	
	int newDir = -1;
	
	public void update(){
		time++;
		time %= 60;
		
		switch(dir){
			case 0: // up
				vfactor = 1;
				mirror = 0x01;
				break;
			case 1: // right
				vfactor = 0;
				mirror = 0x00;
				break;
			case 2: // down
				vfactor = 1;
				mirror = 0x00;
				break;
			case 3: // left
				vfactor = 0;
				mirror = 0x10;
				break;
		}
		move(dir);
	}
	
	public void render(Screen screen){
		Font.write(screen, username, x - (username.length() / 2) * 8 + 8, y - 8, 0xffff00ff);
		if(alive)
			screen.render(tiles[vfactor + (time / 10) % 2], x, y, new Random().nextInt(), mirror);
		else
			screen.render(1 + 0 * 8, x, y, new Random().nextInt(), (byte)0x00);
	}

	public void resurrect(){
		alive = true;
		newDir = -1;
		dir = -1;
		resetPosition();
	}
	
	public void resetPosition(){
		x = sx;
		y = sy;
	}
	
}
