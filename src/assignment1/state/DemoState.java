
package src.assignment1.state;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.entity.*;
import src.assignment1.entity.*;
import src.assignment.state.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public class DemoState extends AbstractPlayState {
	
	/***Constructor***/

	public DemoState(InterfaceGame game, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, 
		InterfaceGameOptions gameOptions, InterfaceGameEntityFactory gameEntityFactory) {
		super(game, stateManager, graphicsManager, audioManager, gameOptions, gameEntityFactory);
	}
	
	/***Methods***/
	
	public void reset(boolean playAudio) {
		super.reset(playAudio);
		playerOne.setAiEnabled(true);
		playerTwo.setAiEnabled(true);
	}
	
	protected void movePaddles(double dt) {
		playerOne.move();
		playerTwo.move();
		
		playerOne.aiMove(dt, ball.getXCoordinate(), ball.getYCoordinate());
		playerTwo.aiMove(dt, ball.getXCoordinate(), ball.getYCoordinate());
		
		playerOne.move(dt);
		playerTwo.move(dt);
	}
		
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode() ;
		
		if (keyCode == e.VK_P) {
			if (paused == true) {	
				paused = false;
			} else {
				paused = true;
			}
		}
		
		if (keyCode == e.VK_ESCAPE) {
			playAudio("score");
			if (gameOver == false) {
				gameOver = true;
			} else {
				cleanup();
				switchState(GameState.Menu.name());
				getCurrentState().reset();
			}
		}	
	}
	
	public String getStateName() { return GameState.Demo.name(); }
}