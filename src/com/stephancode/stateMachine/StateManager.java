package com.stephancode.stateMachine;

import java.util.Stack;

import com.stephancode.stateMachine.components.StateComponent;

public class StateManager {

	private static Stack<StateComponent> stateStack = new Stack<StateComponent>();
	
	public static StateComponent getCurrentState(){ 
		if(stateStack.size() > 0) return stateStack.peek();
		else return null;
	}
	
	public static void pushState(StateComponent state){
		stateStack.push(state);
	}
	
	public static void popState(){
		stateStack.pop();
	}
	
}
