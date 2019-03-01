package src.assignment1.state;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.state.*;
import src.assignment.system.*;

public class ControlsState extends AbstractState {
	
	protected int menuChoice;
	
	/***Constructor***/

	public ControlsState(InterfaceGame game, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager) {
		super(game, stateManager, graphicsManager, audioManager);
	}
	
	/***Methods***/
	
	public void paintComponent() {		
		int fontSize = (int) Math.round(height() * 0.05);
		String font = "Arial";
		double height = height();
		double widthTextAlign = width() * 0.15;
		
		drawImage("Menu", 0, 0, width(), height());
		changeColor(Color.WHITE);
		drawText(widthTextAlign, height * 0.1 , "Player One Up - W", font, fontSize);
		drawText(widthTextAlign, height * 0.2 , "Player One Down - S", font, fontSize);
		drawText(widthTextAlign, height * 0.3 , "Player Two Up - Up Arrow", font, fontSize);
		drawText(widthTextAlign, height * 0.4 , "Player Two Down - Down Arrow", font, fontSize);	
		drawText(widthTextAlign, height * 0.5 , "Pause - P", font, fontSize);
		drawText(widthTextAlign, height * 0.6 , "Confirm - Enter", font, fontSize);
		drawText(widthTextAlign, height * 0.7 , "Back - Escape", font, fontSize);
		drawText(widthTextAlign, height * 0.8 , "Change Options - Left & Right Arrow", font, fontSize);
		changeColor(Color.YELLOW);
		drawText(widthTextAlign, height * 0.9 , "Back to Menu", font, fontSize);
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == e.VK_ENTER || keyCode == e.VK_ESCAPE) {
			playAudio("score");
			menuChoice = 0;
			switchState(GameState.Menu.name()); 
		}
	}
	
	public String getStateName() { return GameState.Controls.name(); }
}