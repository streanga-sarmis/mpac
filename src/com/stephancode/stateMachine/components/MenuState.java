package com.stephancode.stateMachine.components;

import com.stephancode.entities.MPlayer;
import com.stephancode.graphics.Font;
import com.stephancode.graphics.Screen;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.main.KeyHandler;
import com.stephancode.stateMachine.StateManager;

public class MenuState extends StateComponent{

	boolean start = false;
	int time = 5 * 60;
	MPlayer other;
	
	public MenuState(){
		
	}
	
	public void update(KeyHandler keys) {
		if(start) 
			time--;
	}

	public void render(Screen screen) {
		screen.clear(0x333333);
		Font.write(screen, "Wait for the other player to join !!!", 24, 16, 0xeeeeee);
		
		if(start) 
			Font.write(screen, "Starting in " + time / 60 + " seconds.", 32, 32, 0xeeeeee);

		if(time == 0){
			StateManager.pushState(new PlayState());
			LevelManager.addOtherPlayer(other);
		}
	}

	public void dispose() {
		
	}
	
	public void startTimer(MPlayer other) {
		start = true;
		this.other = other;
	}
	
	
}
