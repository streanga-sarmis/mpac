package com.stephancode.entities.ai;

import java.util.ArrayList;

import com.stephancode.levelMachine.LevelManager;
import com.stephancode.levelMachine.components.tiles.Tile;

public class ANode {
	
	public int x, y; // tile coords
	
	public ANode parent;

	public int gCost = Integer.MAX_VALUE;
	public int fCost = Integer.MAX_VALUE;
	public int hCost = Integer.MAX_VALUE;
	
	public boolean solid = false;

	public ArrayList<ANode> neighbours = new ArrayList<ANode>();
	
	public ANode(int x, int y, boolean solid){
		this.x = x;
		this.y = y;
		this.solid = solid;
	}
	
	public boolean equals(ANode other){
		return (this.x == other.x && this.y == other.y) ;
	}
	
	public void generateNeighbours(int x, int y, int[] tiles){
		int w = LevelManager.getCurrentLevel().w;
		int h = LevelManager.getCurrentLevel().h;
		
		if(x >= 0 && y >= 0 && x < w && y < h){
			if(x + 1 < w)
				neighbours.add(new ANode(x + 1, y, Tile.tiles[tiles[(x + 1) + y * LevelManager.getCurrentLevel().w]].solid()));
			if(x - 1 >= 0)
				neighbours.add(new ANode(x - 1, y, Tile.tiles[tiles[(x - 1) + y * LevelManager.getCurrentLevel().w]].solid()));
			if(y + 1 < h)
				neighbours.add(new ANode(x, y + 1, Tile.tiles[tiles[x + (y + 1) * LevelManager.getCurrentLevel().w]].solid()));
			if(y - 1 >= 0)
				neighbours.add(new ANode(x, y - 1, Tile.tiles[tiles[x + (y - 1) * LevelManager.getCurrentLevel().w]].solid()));
		}
	}
	
}
