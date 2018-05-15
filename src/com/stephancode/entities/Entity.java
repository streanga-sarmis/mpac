package com.stephancode.entities;

import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.main.MainComponent;

public class Entity {

	protected int hp = 10;
	protected int w;
	protected int h;
	public int x;
	public int y;
	public int dir = 1; // 0^ 1> 2v 3< best description ever
	public String username = "#missingno";
	public boolean alive = true;
	protected boolean canCollide = true;
	protected boolean removed = false;
	
	public void update(){}
	public void render(Screen screen){}
	
	protected void move(int xa, int ya){
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if(canCollide){
			if(canPass(xa, ya)){
				this.x += xa;
				this.y += ya;
				if(this instanceof Player)
					MainComponent.c.send("01#" + username + "#" + x + "#" + y);
			} else {
				return;
			}
		} else {
			if(this.x + xa < 0) return;
			if(this.x + xa >= LevelManager.getCurrentLevel().w) return;
			if(this.y + ya < 0) return;
			if(this.y + ya >= LevelManager.getCurrentLevel().h) return;
		}
	}
	
	protected void move(int dir){
		switch(dir){
		case 0: // up
			move(0, -1);
			break;
		case 1: // right
			move(1, 0);
			break;
		case 2: // down
			move(0, 1);
			break;
		case 3: // left
			move(-1, 0);
			break;
		}
	}
	
	protected boolean canPass(int xa, int ya){
		int x0 = (this.x + xa) >> 4;
		int x1 = (this.x + 15 + xa) >> 4;
		int y0 = (this.y + ya) >> 4;
		int y1 = (this.y + 15 + ya) >> 4;
		
		if(LevelManager.getCurrentLevel().getTile(x0, y0).solid()) return false;
		if(LevelManager.getCurrentLevel().getTile(x1, y0).solid()) return false;
		if(LevelManager.getCurrentLevel().getTile(x0, y1).solid()) return false;
		if(LevelManager.getCurrentLevel().getTile(x1, y1).solid()) return false;
		
		return true;
	}
	
	public void setDir(int dir){
		this.dir = dir;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	protected void remove(){ removed = true; }
	public boolean getRemoved(){ return removed; }
	public boolean touchedPlayer(){
		if(x + w > LevelManager.getPlayer().x && x < LevelManager.getPlayer().x + LevelManager.getPlayer().w &&
		   y + h > LevelManager.getPlayer().y && y < LevelManager.getPlayer().y + LevelManager.getPlayer().h) return true;
		return false;
	}

	public boolean touchedMPlayer(){
		if(x + w > LevelManager.getMPlayer().x && x < LevelManager.getMPlayer().x + LevelManager.getMPlayer().w &&
		   y + h > LevelManager.getMPlayer().y && y < LevelManager.getMPlayer().y + LevelManager.getMPlayer().h) return true;
		return false;
	}
	
}
