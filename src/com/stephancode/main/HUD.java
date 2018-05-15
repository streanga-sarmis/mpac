package com.stephancode.main;

import com.stephancode.graphics.Font;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;

public class HUD {
	
	public static void render(Screen hud){

		hud.clear(0x333333);

		Font.write(hud, "Hello " + LevelManager.player.username, 8, 8, 0xeeeeee);
		Font.write(hud, "Score: " + LevelManager.player.score, 8, 19, 0xeeeeee);
		Font.write(hud, "...........................................", 8, 16 + 16, 0xeeeeee);
		
	}
	
}
