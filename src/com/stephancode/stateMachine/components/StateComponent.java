package com.stephancode.stateMachine.components;

import com.stephancode.graphics.Screen;
import com.stephancode.main.KeyHandler;

public abstract class StateComponent {
	
	public abstract void update(KeyHandler keys);
	public abstract void render(Screen screen);
	public abstract void dispose();

}
