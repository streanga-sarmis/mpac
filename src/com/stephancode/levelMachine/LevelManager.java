package com.stephancode.levelMachine;

import java.util.Stack;

import com.stephancode.entities.MPlayer;
import com.stephancode.entities.Player;
import com.stephancode.levelMachine.components.LevelComponent;
import com.stephancode.stateMachine.StateManager;
import com.stephancode.stateMachine.components.GameOverState;

public class LevelManager {
	
	public static int foodCount = 0;
	public static int stageWaitTime = 60 * 4;
	public static int stageNum = 1;
	public static Player player = new Player();
	public static MPlayer other;
	
	private static Stack<LevelComponent> levelStack = new Stack<LevelComponent>();
	
	public static LevelComponent getCurrentLevel(){ 
		if(levelStack.size() > 0) return levelStack.peek();
		else return null;
	}
	
	public static void pushLevel(LevelComponent level){
		levelStack.push(level);
	}
	
	public static void popLevel(){
		getCurrentLevel().clearAll();
		levelStack.pop();
	}

	public static Player getPlayer() { return player; }
	public static MPlayer getMPlayer(){ return other; }

	public static void addOtherPlayer(MPlayer player) {
		other = player;
		getCurrentLevel().setMPlayer(other);
	}
	
	public static void checkDead(){
		if(!player.alive && !other.alive){
			// reset level here
			popLevel();
			player.score = 0;
			
			StateManager.popState();
			StateManager.pushState(new GameOverState());

			
		}
	}
	
	public static void checkFood(){
		if(player.score == foodCount){
			nextStage();
		}
	}
	
	public static void nextStage(){
		if(player.alive) {
			player.resetPosition();
		} else {
			player.resurrect();
		}

		if(other.alive) {
			other.resetPosition();
		} else {
			other.resurrect();
		}
		
		popLevel();
		++stageNum;
		foodCount = 0;
		stageWaitTime = 60 * (4 - stageNum / 2);
		pushLevel(new LevelComponent(20 + 5, 12 + 5));
		getCurrentLevel().setMPlayer(other);
	}
	
}
