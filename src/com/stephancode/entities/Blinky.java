package com.stephancode.entities;

import java.util.LinkedList;
import java.util.Random;

import com.stephancode.entities.ai.ANode;
import com.stephancode.entities.ai.AStar;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;

public class Blinky extends Entity{

	private Random r = new Random(666);
	// @TODO maybe implement some AI for more AIs, maybe...
	private LinkedList<ANode> path; 
	private int time = 0;
	private AStar astar = new AStar();
	
	
	private int swapAmount = 60;
	private int swapTime = 0;
	
	private boolean changedPath = false;
	
	private Entity target;
	
	public Blinky(int tx, int ty){
		this.x = tx * 16;
		this.y = ty * 16;
		this.w = 12;
		this.h = 12;
		this.dir = -1;
	}
	// @CleanUp
	ANode start;
	ANode finish;
	int oldX;
	int oldY;
	int ptx;
	int pty;
	
	public void update(){
		swapTime++;
		swapTime %= swapAmount;
		
		if(target != null){
			if(!target.alive){
				target = LevelManager.getCurrentLevel().getEntityByName(
						LevelManager.getCurrentLevel().getNextUsername());
				changedPath = true;
			}
		}
		
		if(touchedPlayer()){
			LevelManager.getPlayer().alive = false;
		}

		if(touchedMPlayer()){
			LevelManager.getMPlayer().alive = false;
		}
		
		if(swapTime == 0){
			target = LevelManager.getCurrentLevel().getEntityByName(
					LevelManager.getCurrentLevel().getNextUsername());
			changedPath = true;
		}
		if(target != null){
			ptx = (target.x) >> 4;
			pty = (target.y) >> 4;
	
			if(time == 0){
				int tx = this.x >> 4;
				int ty = this.y >> 4;
				
				start = new ANode(tx, ty, false);
				finish = new ANode(ptx, pty, false);
				path = astar.getPathNodes(start, finish);
			}
			
			time++;
			time %= 2;
			
			if(path != null){
				if(path.size() - 2 > 0){
					if(path.get(path.size() - 2).x * 16 != this.x && (canPass(1, 0) || canPass(-1 , 0))){
						if(path.get(path.size() - 2).x * 16 > this.x) dir = 1;
						else if(path.get(path.size() - 2).x * 16 < this.x) dir = 3;
					} else if(path.get(path.size() - 2).y * 16 != this.y && (canPass(0, -1) || canPass(0 , 1))){
						if(path.get(path.size() - 2).y * 16 > this.y) dir = 2;
						else if(path.get(path.size() - 2).y * 16 < this.y) dir = 0;
					}
				}
			}
			oldX = x;
			oldY = y;
			move(dir);
			if(oldX != x && oldY != y){
				 LevelManager.getCurrentLevel().setMobToTile(oldX >> 4, oldY >> 4, 0);
				 LevelManager.getCurrentLevel().setMobToTile(x >> 4, y >> 4, 1);
			}
		}
		
		if(hp <= 0) remove();
		
	}
	
	public void render(Screen screen){
//		@CleanUp
		
		if(path != null)
		if(path.size() > 0){
			for(int i = 0; i < path.size(); i++){
				screen.render(7 + 0 * 8, path.get(i).x * 16, path.get(i).y * 16, 0xffff00ff, (byte)0x00);
			}
		}
		
		screen.render(0 + 0 * 8, x, y, 0xff00ff, (byte)0x00);
	}
	
}
