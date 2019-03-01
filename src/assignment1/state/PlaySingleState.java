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

public class PlaySingleState extends AbstractPlayState {
	
	/***Properties***/
	
	AiPlayer aiPlayer;
	
	/***Constructor***/

	public PlaySingleState(InterfaceGame game, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, 
		InterfaceGameOptions gameOptions, InterfaceGameEntityFactory gameEntityFactory) {
		super(game, stateManager, graphicsManager, audioManager, gameOptions, gameEntityFactory);
		aiPlayer = gameOptions.getAiPlayer();
	}
	
	/***Methods***/
	
	public void reset(boolean playAudio) {
		super.reset(playAudio);
		aiPlayer = gameOptions.getAiPlayer();
		if(aiPlayer == AiPlayer.One) { 
			playerOne.setAiEnabled(true);
		} else if (aiPlayer == AiPlayer.Two){
			playerTwo.setAiEnabled(true);
		}
	}
	
	protected void movePaddles(double dt) {
		playerOne.move();
		playerTwo.move();
		
		if (playerOne.isAiEnabled() == true) { playerOne.aiMove(dt, ball.getXCoordinate(), ball.getYCoordinate()); } 
		if (playerTwo.isAiEnabled() == true) { playerTwo.aiMove(dt, ball.getXCoordinate(), ball.getYCoordinate()); }
		
		playerOne.move(dt);
		playerTwo.move(dt);
	}
		
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode() ;
		
		double height = height();
		double playerOneHeight = playerOne.getHeight();
		double playerTwoHeight = playerTwo.getHeight();
		
		if (playerOne.isAiEnabled() == false) {
			if (keyCode == e.VK_W) {
				if (playerOne.getYCoordinate() >  playerOneHeight / 2) { playerOne.setUp(true); }
			}
		
			if (keyCode == e.VK_S) { 
				if (playerOne.getYCoordinate() < height - playerOneHeight/ 2) {  playerOne.setDown(true); }
			}
		}

		if (playerTwo.isAiEnabled() == false) {
			
			if (keyCode == e.VK_UP) {
				if (playerTwo.getYCoordinate() > playerTwoHeight / 2) { playerTwo.setUp(true); }
			}
		
			if (keyCode == e.VK_DOWN) {
				if (playerTwo.getYCoordinate() < height - playerTwoHeight / 2) { playerTwo.setDown(true); }
			}
		}
	
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
	
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode() ;
		
		if (playerOne.isAiEnabled() == false) {
			if (keyCode == e.VK_W) {
				playerOne.setYVelocity(0);
				playerOne.setUp(false);
			}
		
			if (keyCode == e.VK_S) { 
				playerOne.setYVelocity(0);
				playerOne.setDown(false);		
			}
		} 

		if (playerTwo.isAiEnabled() == false) {
			if (keyCode == e.VK_UP) {
				playerTwo.setYVelocity(0);
				playerTwo.setUp(false);				
			}
			if (keyCode == e.VK_DOWN) {
				playerTwo.setYVelocity(0);
				playerTwo.setDown(false);			
			} 
		}
	}
	
	public String getStateName() { return GameState.PlaySingle.name(); }
}
