package com.stephancode.stateMachine.components;

import com.stephancode.entities.MPlayer;
import com.stephancode.graphics.Font;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.main.KeyHandler;
import com.stephancode.stateMachine.StateManager;

public class GameOverState extends StateComponent{

	int time = 5 * 60;
	
	public GameOverState(){
		
	}
	
	public void update(KeyHandler keys) {
		time--;
	}

	public void render(Screen screen) {
		screen.clear(0x333333);
		Font.write(screen, "Game over!", 32, 32, 0xeeeeee);
		Font.write(screen, "Starting in " + time / 60 + " seconds.", 32, 64, 0xeeeeee);

		if(time == 0){
			LevelManager.other.resurrect();
			LevelManager.player.resurrect();
			
			StateManager.pushState(new PlayState());
			LevelManager.addOtherPlayer(LevelManager.other);
		}
	}

	public void dispose() {
		
	}
	
}
