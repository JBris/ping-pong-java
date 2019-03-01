package src.assignment.state;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.system.*;

public abstract class AbstractState implements InterfaceState {
	
	/***Properties***/
		
	protected InterfaceGame game;
	
	protected InterfaceStateManager stateManager;
	protected InterfaceGraphicsManager graphicsManager;
	protected InterfaceAudioManager audioManager;

	/***Constructor***/
	
	public AbstractState(InterfaceGame game, InterfaceStateManager stateManager, InterfaceGraphicsManager graphicsManager, 
		InterfaceAudioManager audioManager) {
		this.game = game;
		this.stateManager = stateManager;
		this.graphicsManager = graphicsManager;
		this.audioManager = audioManager;
	}
	
	/***Methods***/
	
	public void reset() {}

	public void reset(boolean playAudio) {}
	
	public void update(double dt) {}
	
	public void paintComponent() {}
	
	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	public abstract String getStateName();

	protected void switchState(String state) {
		stateManager.switchState(state);
	}
	
	protected InterfaceState getCurrentState() {
		return stateManager.getCurrentState();
	}
	
	protected void changeColor(Color c) {
		graphicsManager.changeColor(c);
	}
	
	protected void drawText(double x, double y, String s, String font, int size) {
		graphicsManager.drawText(x, y, s, font, size);
	}
	
	protected void drawLine(double x1, double y1, double x2, double y2, double l) {
		game.drawDisplayLine(x1, y1, x2, y2, l);
	}
	
	protected void drawMenuText (int menuChoice, int menuItem, double width, double height, String text) {
		graphicsManager.drawMenuText(menuChoice, menuItem, width, height, text);
	}
	
	protected void drawImage(String name, double x, double y, double w, double h) {
		graphicsManager.drawImage(name, x, y, w, h);
	}

	protected void playAudio(String name) {
		audioManager.playAudio(name);
	}
	
	protected double height() {
		return game.height();
	}
	
	protected double width() {
		return game.width();
	}
		
	protected void sleep(double duration) {
		game.sleep(duration);
	}
}