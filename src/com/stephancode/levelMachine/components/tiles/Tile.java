package com.stephancode.levelMachine.components.tiles;

import com.stephancode.graphics.Screen;

public class Tile {

	public static Tile tiles[] = new Tile[256]; // maximum 256 tiles, overkill but whatever
	public boolean solid = false;
	public int id;
	
	public static Tile air = new AirTile(0x00);
	public static Tile wall = new WallTile(1);
	
	public Tile(int id){
		this.id = id;
		tiles[id] = this;
	}
	
	public void render(Screen screen, int xa, int ya){};
	
	public boolean solid() { return solid; }
	
}
