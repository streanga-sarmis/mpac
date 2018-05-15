package com.stephancode.stateMachine.components;

import com.stephancode.graphics.Font;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.levelMachine.components.LevelComponent;
import com.stephancode.main.KeyHandler;

public class PlayState extends StateComponent{

	public PlayState(){
		LevelManager.pushLevel(new LevelComponent(20 + 5, 12 + 5));
	}
	
	public void update(KeyHandler keys) {
		LevelManager.getCurrentLevel().update(keys);
		LevelManager.checkDead();
		LevelManager.checkFood();
	}

	public void render(Screen screen) {
		screen.clear(0x333333);
		LevelManager.getCurrentLevel().render(screen);		
	}

	public void dispose() {
		
	}
	
}
