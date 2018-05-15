package com.stephancode.levelMachine.components.tiles;

import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;

public class WallTile extends Tile {

	
	public WallTile(int id) {
		super(id);
		solid = true;
	}

	public void render(Screen screen, int xa, int ya){
		
		int hoffseted = ya + 4;
		
		boolean u = LevelManager.getCurrentLevel().getTile(xa, ya - 1) == this;
		boolean d = LevelManager.getCurrentLevel().getTile(xa, ya + 1) == this;
		boolean l = LevelManager.getCurrentLevel().getTile(xa - 1, ya) == this;
		boolean r = LevelManager.getCurrentLevel().getTile(xa + 1, ya) == this;

		if(!u && l && r && d) screen.render(3 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(!u && !l && r && d) screen.render(2 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x01);
		if(!u && l && !r && d) screen.render(2 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x11);
	
		if(!u && !l && !r && d) screen.render(1 + 4 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(u && !l && !r && !d) screen.render(1 + 4 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x01);

		if(u && l && r && !d) screen.render(3 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x01);
		if(u && l && !r && !d) screen.render(2 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x10);
		if(u && !l && r && !d) screen.render(2 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(!u && !l && r && !d) screen.render(1 + 2 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(!u && l && !r && !d) screen.render(1 + 2 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x10);
		

		if(u && !l && r && d) screen.render(0 + 4 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x10);
		if(u && l && !r && d) screen.render(0 + 4 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(u && !l && !r && d) screen.render(1 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(!u && l && r && !d) screen.render(2 + 2 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		if(u && l && r && d) screen.render(0 + 3 * 8, xa * 16, ya * 16, 0xffffff, (byte)0x00);
		
	}
	
}
