package src.assignment1.state;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.state.*;
import src.assignment.system.*;

public class MenuState extends AbstractState {
	
	/***Properties***/

	protected int menuChoice;
	
	/***Constructor***/

	public MenuState(InterfaceGame game, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager) {
		super(game, stateManager, graphicsManager, audioManager);
	}
	
	/***Methods***/
	
	public void paintComponent() {		
		double menuWidth = game.width() * 0.25;
		int height = game.height();
		
		drawImage("Menu", 0, 0, width(), height());
		game.changeColor(Color.GREEN);
		drawText(game.width()  * 0.35, height * 0.15 , "Pong", "Arial", (int) (game.height() * 0.08));
		drawMenuText(menuChoice, 0, menuWidth, height * 0.3, "Single Player");
		drawMenuText(menuChoice, 1, menuWidth, height * 0.4, "Two Player");
		drawMenuText(menuChoice, 2, menuWidth, height * 0.5, "Demo Mode");
		drawMenuText(menuChoice, 3, menuWidth, height * 0.6, "Options");
		drawMenuText(menuChoice, 4, menuWidth, height * 0.7, "Controls");
		drawMenuText(menuChoice, 5, menuWidth, height * 0.8, "Exit");
	}
	
	public void reset() {
		audioManager.startAudioLoop("Menu");
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode() ;
		if (keyCode == e.VK_ESCAPE) { System.exit(0); }
		
		if (keyCode == e.VK_UP) {
			playAudio("beep");
			if (menuChoice <= 0) { 
				menuChoice = 5; 
			} else {
				menuChoice --;
			}
		}
		
		if (keyCode == e.VK_DOWN) {
			playAudio("beep");
			if (menuChoice >= 5) { 
				menuChoice = 0; 
			} else {
				menuChoice ++;
			}
		}
		
		if (keyCode == e.VK_ENTER) {
			playAudio("score");
			if (menuChoice == 0) {
				switchState(GameState.PlaySingle.name());
				getCurrentState().reset(true);
				sleep(500);
			} else if (menuChoice == 1) {
				switchState(GameState.PlayMulti.name());
				getCurrentState().reset(true);
				game.sleep(500);
			} else if (menuChoice == 2) {
				switchState(GameState.Demo.name());
				getCurrentState().reset(true);
				game.sleep(500);
			} else if (menuChoice == 3) {
				menuChoice = 0;
				switchState(GameState.Options.name());
			} else if (menuChoice == 4) {
				menuChoice = 0;
				switchState(GameState.Controls.name());
			} else if (menuChoice == 5) {
				System.exit(0);
			}
		
		}
	}
	
	public String getStateName() { return GameState.Menu.name(); }
}