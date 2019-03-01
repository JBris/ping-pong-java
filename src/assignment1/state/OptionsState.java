package src.assignment1.state;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.state.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public class OptionsState extends AbstractState {
	
	/***Properties***/

	protected int menuChoice;
	
	protected InterfaceGameOptions gameOptions;
	
	/***Constructor***/

	public OptionsState(InterfaceGame game, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, InterfaceGameOptions gameOptions) {
		super(game, stateManager, graphicsManager, audioManager);
		this.gameOptions = gameOptions;
	}
	
	/***Methods***/
	
	public void paintComponent() {		
		double menuWidth = width() * 0.15;
		double height = height();
		drawImage("Menu", 0, 0, width(), height());
		drawMenuText(menuChoice, 0, menuWidth, height * 0.2, "Difficulty: " + gameOptions.getDifficulty().name());
		drawMenuText(menuChoice, 1, menuWidth, height * 0.3, "Number of Rounds: " + Integer.toString(gameOptions.getNumberOfRounds()));
		drawMenuText(menuChoice, 2, menuWidth, height * 0.4, "AI Player: Player " + gameOptions.getAiPlayer().name());
		drawMenuText(menuChoice, 3, menuWidth, height * 0.5, "Back to Menu");	
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == e.VK_ESCAPE) {
			playAudio("score");
			menuChoice = 0;
			switchState(GameState.Menu.name()); 
		}
		
		if (keyCode == e.VK_UP) {
			playAudio("beep");
			if (menuChoice <= 0) { 
				menuChoice = 3; 
			} else {
				menuChoice -- ;
			}
		}
		
		if (keyCode == e.VK_DOWN) {
			playAudio("beep");
			if (menuChoice >= 3) { 
				menuChoice = 0; 
			} else {
				menuChoice ++;
			}
		}
		
		if (keyCode == e.VK_ENTER && menuChoice == 3) {
			playAudio("score");
			menuChoice = 0;
			switchState(GameState.Menu.name()); 
		}
		
		changeOptions(e);
	}
	
	public void changeOptions(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == e.VK_LEFT) {
			playAudio("beep");	
			switch(menuChoice) {
				
				case 0:
					Difficulty difficulty = gameOptions.getDifficulty();
					if (difficulty == Difficulty.Hard) {
						gameOptions.setDifficulty(Difficulty.Medium);
					} else if (difficulty == Difficulty.Medium) {
						gameOptions.setDifficulty(Difficulty.Easy);
					} else {
						gameOptions.setDifficulty(Difficulty.Hard);
					}
					break;
					
				case 1:
					int numberOfRounds = gameOptions.getNumberOfRounds();
					if (numberOfRounds <= 1) {
						gameOptions.setNumberOfRounds(15);
					} else {
						numberOfRounds--;
						gameOptions.setNumberOfRounds(numberOfRounds); 
					}
					break;
				
				case 2: 
					AiPlayer aiPlayer = gameOptions.getAiPlayer();
					if (aiPlayer == AiPlayer.Two) {
						gameOptions.setAiPlayer(AiPlayer.One);
					} else {
						gameOptions.setAiPlayer(AiPlayer.Two);
					}
					break;
			}
		}
	
		if (keyCode == e.VK_RIGHT) {
			playAudio("beep");	
			switch(menuChoice) {
				
				case 0:
					Difficulty difficulty = gameOptions.getDifficulty();
					if (difficulty == Difficulty.Easy) {
						gameOptions.setDifficulty(Difficulty.Medium);
					} else if (difficulty == Difficulty.Medium) {
						gameOptions.setDifficulty(Difficulty.Hard);
					} else {
						gameOptions.setDifficulty(Difficulty.Easy);
					}
					break;
					
				case 1:
					int numberOfRounds = gameOptions.getNumberOfRounds();
					if (numberOfRounds >= 15) {
						gameOptions.setNumberOfRounds(1); 
					} else {
						numberOfRounds++;
						gameOptions.setNumberOfRounds(numberOfRounds); 
					}
					break;
				
				case 2: 
					AiPlayer aiPlayer = gameOptions.getAiPlayer();
					if (aiPlayer == AiPlayer.Two) {
						gameOptions.setAiPlayer(AiPlayer.One);
					} else {
						gameOptions.setAiPlayer(AiPlayer.Two);
					}
					break;
			}
		}	
	}
	
	public String getStateName() { return GameState.Options.name(); }
}