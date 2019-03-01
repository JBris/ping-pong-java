package src.assignment.state;

import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.system.*;

public class StateManager implements InterfaceStateManager {
	
	/***Properties***/
	
	protected InterfaceGame game;

	protected Map<String, InterfaceState> stateList;
	
	protected InterfaceState currentState;
	
	/***Constructor***/
	
	public StateManager(InterfaceGame game, InterfaceState initialState) {
		this.game = game;
		stateList = new HashMap<String, InterfaceState>();
		currentState = initialState;
	}
	
	/***Methods***/
	
	public void registerState(InterfaceState state) {
		stateList.put(state.getStateName(), state);
	}
	
	public InterfaceState getCurrentState() {
		return currentState;
	}

	public void switchState(String stateName) {
		InterfaceState newState = stateList.get(stateName);
		if (newState == null) {
			throw new NullPointerException(String.format("State %s has not been registered.", stateName));
		} 
		currentState = newState;
	}
	
	public void paintComponent() {
		currentState.paintComponent();
	}
	
	public void update(double dt) {
		currentState.update(dt);
	}
	
	public void keyPressed(KeyEvent e) { 
		currentState.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		currentState.keyReleased(e);
	}
	
	public void mouseExited(MouseEvent e) {
		currentState.mouseExited(e);
	}
	
	public String getServiceId() { return "StateManager"; }
}