package com.stephancode.levelMachine.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.stephancode.entities.Entity;
import com.stephancode.entities.MPlayer;
import com.stephancode.entities.Player;
import com.stephancode.entities.ai.ANode;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.levelMachine.components.tiles.Tile;
import com.stephancode.main.KeyHandler;

public class LevelComponent {

	private List<Entity> entities = new LinkedList<Entity>();
	//private MPlayer otherPlayer = null;
	private Player player;
	private MPlayer other;
	
	private int currentUsername = 0;
	private ArrayList<String> availableUsernames = new ArrayList<String>();
	
	private String preName = "";
	
	public int w;
	public int h;
	private int[] tiles;
	private int[] mobsOnTiles;
	private LevelParser parser;
	
	public LevelComponent(int w, int h){
		this.w = w;
		this.h = h;
		
		tiles = new int[w * h];
		mobsOnTiles = new int[w * h];
		
		Arrays.fill(mobsOnTiles, 0);

		this.player = LevelManager.player;
		availableUsernames.add(player.username);
		
		addEntity(player);
		
		parser = new LevelParser(this, "/map.png");
		tiles = parser.getTiles();
	}
	
	public void update(KeyHandler keys){
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			if(e.getRemoved()){ 
				entities.remove(e);
				continue;
			}
			
			if(e instanceof Player)
				((Player)e).update(keys);
			else if(e != null )e.update();
		}
	}
	
	public void render(Screen screen){
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				Tile t = getTile(x, y);
				if(t.id != Tile.air.id) t.render(screen, x, y);
			}
		}
		
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			if (e != null) e.render(screen);
		}
	}
	
	public Tile getTile(int xa, int ya){
		if(xa >= w || xa < 0 || ya >= h || ya < 0) return Tile.air;
		return Tile.tiles[tiles[xa + ya * w]];
	}

	public int[] getTiles(){ return tiles; }
	
	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void setMPlayer(MPlayer player) { 
		this.other = player;
		availableUsernames.add(other.username);
		addEntity(other);
	}
	
	public String getNextUsername(){
		currentUsername++;
		if(!getEntityByName(availableUsernames.get(currentUsername % 2)).alive){
			currentUsername--;
		}
		
		if(availableUsernames.size() == 0){
			return null;
		}
		return availableUsernames.get(currentUsername % 2);
	}
	
	public Entity getEntityByName(String username){
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			if(e != null)
				if(e.username.equals(username)) return e;
		}
		return null;
	}
	
	public void setMobToTile(int x, int y, int data){
		mobsOnTiles[x + y * w] = data;
	}
	
	public boolean mobOnTile(int x, int y){
		return (mobsOnTiles[x + y * w] == 1);
	}
	
	public void clearAll(){
		availableUsernames.clear();
		entities.clear();
	}
	
}
