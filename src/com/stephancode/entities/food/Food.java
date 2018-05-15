package com.stephancode.entities.food;

import java.util.Random;

import com.stephancode.entities.Entity;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;

public class Food extends Entity{

	protected int foodScore;
	protected int tiles[] = new int[2];
	protected int animationIndex = 0;
	protected int time = 0;
	protected Random r = new Random();
	protected int animationTime;
	
	public Food(int x, int y, int foodScore){
		this.x = x * 16 + 8;
		this.y = y * 16 + 8;
		this.w = 4;
		this.h = 4;
		this.foodScore = foodScore;
		tiles[0] = 7 + 1 * 8;
		tiles[1] = 7 + 2 * 8;
		animationTime = r.nextInt(30) + 20;
	}
	
	public void update(){
		if(touchedPlayer() || touchedMPlayer()){
			remove();
			LevelManager.player.score++;
		}
		time++;
		time %= 60;
		
		if(time < animationTime) animationIndex = 0;
		else animationIndex = 1;
	}

	public void render(Screen screen){
		screen.render(tiles[animationIndex], x - 8, y - 8, 0xffffff, (byte)0x00);
	}
	
}
