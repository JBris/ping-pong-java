package src.assignment1.state;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment1.entity.*;
import src.assignment.state.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public abstract class AbstractPlayState extends AbstractState implements InterfaceScoreWatcher {
	
	/***Properties***/
	
	protected InterfaceGameOptions gameOptions;
	InterfaceGameEntityFactory gameEntityFactory;
	
	protected boolean gameOver, paused, countDownState; 
	protected int countDownTime;
	protected double countDownTimer;
	
	protected InterfacePaddle playerOne, playerTwo;
	protected InterfaceBall ball;
	
	protected String currentMusic;
	
	/***Constructor***/

	public AbstractPlayState(InterfaceGame game, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, 
		InterfaceGameOptions gameOptions, InterfaceGameEntityFactory gameEntityFactory) {
		super(game, stateManager, graphicsManager, audioManager);
		this.gameOptions = gameOptions;
		this.gameEntityFactory = gameEntityFactory;
			
		playerOne = gameEntityFactory.createPaddle();
		playerOne.setName("1");
		playerTwo = gameEntityFactory.createPaddle();
		playerTwo.setName("2");
		ball = gameEntityFactory.createBall(this);
		reset(false);
	}
	
	/***Methods***/
	
	public void reset(boolean playAudio) {
		gameOver = false;
		paused = false;
		countDownState = true;
		countDownTimer = 0;
		countDownTime = 4;
		
		setGameDifficulty();
		playerOne.reset(width() * 0.05);
		playerTwo.reset(width() * 0.95);
		ball.reset();

		if (playAudio == true) {
			audioManager.stopAudioLoop("Menu");
			audioManager.shufflePlayList();
			currentMusic = audioManager.getNameFromPlayList(0);
		}
	}
	
	public void paintComponent() {
		int fontSize = (int) Math.round(height() * 0.05);
		String font = "Arial";
		double width = width();
		double height = height();
		int playerOneScore = playerOne.getScore();
		int playerTwoScore = playerTwo.getScore();

		drawImage("Play", 0, 0, width(), height());		
		if(gameOver == false) {
			changeColor(Color.WHITE);
			drawText(width * 0.2, height * 0.1,  Integer.toString(playerOneScore), font, fontSize);
			drawText(width * 0.8, height * 0.1,  Integer.toString(playerTwoScore), font, fontSize);
					
			changeColor(Color.GREEN);
			drawLine(0.0, 0.0, width, 0.0, height * 0.03);
			drawLine(0.0, height, width, height, height * 0.03);

			for (double i = 0; i < 1; i += 0.15) {
				double startingPosition = i * height;
				drawLine(width / 2 , startingPosition, width / 2, startingPosition + height * 0.1, height * 0.02);
			}
			
			ball.render();
			playerOne.render();
			playerTwo.render();
			
			if (countDownState == true) {
				changeColor(Color.RED);
				if (countDownTime > 0 && countDownTime < 4) { 
					drawText(width * 0.45, height * 0.3 , Integer.toString(countDownTime), font, fontSize * 3); 
				} else if (countDownTime == 0){ 
					drawText(width * 0.4, height * 0.3 , "GO!", font, fontSize * 3); 
				} 
			} else if (paused == true) {
				changeColor(Color.RED);
				drawText(width * 0.35, height / 2 , "Paused", font, fontSize * 2); 
			}
			
		} else {		
			double widthTextAlign = width() * 0.4;
			changeColor(Color.WHITE);			
			drawText(widthTextAlign, height * 0.3 , "Game Over!", font, fontSize);
			drawText(widthTextAlign, height * 0.4 , String.format("Player 1: %d", playerOneScore) , font, fontSize);
			drawText(widthTextAlign, height * 0.5 , String.format("Player 2: %d", playerTwoScore) , font, fontSize);

			int winner = determineWinner();
			if (winner == 0) { 
				drawText(widthTextAlign, height * 0.6 , "You have tied", "Arial", fontSize);
			} else {
				drawText(widthTextAlign, height * 0.6 , String.format("Player %d wins!", winner) , font, fontSize);
			}
			
			drawText(widthTextAlign, height * 0.7 , "Press ESC to reset", font, fontSize);
		}
	}
	
	public void incrementScore(int player) {
		if (player == 1) {
			playerOne.incrementScore();
		} else if (player == 2){
			playerTwo.incrementScore();
		}
	}
		
	public void mouseExited(MouseEvent e) {
		paused = true; 
	}
	
	public void update(double dt) {
		
		if (countDownState == true) {
			countDownTimer += dt;
			if (countDownTimer < 1) { return; }
			countDownTimer -= 1;
			countDownTime --;

			if(countDownTime < 0) { 
				countDownState = false; 
			} else if (countDownTime == 0) {
				audioManager.startAudioLoop(currentMusic);
			} else {
				playAudio("beep");
			}
			return;
		}
		
		if (gameOver == true || paused == true) { return; }
		int numberOfRounds = gameOptions.getNumberOfRounds();
		if (playerOne.getScore() >= numberOfRounds || playerTwo.getScore() >= numberOfRounds) { gameOver = true; }
		ball.collide();
		movePaddles(dt);
		ball.collide(playerOne);
		ball.collide(playerTwo);
		ball.move(dt);
		ball.update(dt);
	}
	
	protected void movePaddles(double dt) {
		playerOne.move();
		playerTwo.move();
		playerOne.move(dt);
		playerTwo.move(dt);
	}
	
	protected int determineWinner() {
		if (playerOne.getScore() > playerTwo.getScore()) {
			return 1;
		} else if (playerTwo.getScore() > playerOne.getScore()) {
			return 2;
		} else {
			return 0;
		}
	} 

	protected void cleanup() {
		audioManager.stopAudioLoop(currentMusic);
		game.sleep(500);
	}
	 
	protected void setGameDifficulty() {
		if (gameOptions.getDifficulty() == Difficulty.Easy) {
			playerOne.setVelocity(600);
			playerTwo.setVelocity(600);
			
			playerOne.setAiSpeed(600 * 0.85);
			playerTwo.setAiSpeed(600 * 0.85);
			
			playerOne.setReactionTime(0.25);
			playerTwo.setReactionTime(0.25);
			
			playerOne.setErrorMultiplier(0.15);
			playerTwo.setErrorMultiplier(0.15);
	
			playerOne.setHeight(height() * 0.2);
			playerTwo.setHeight(height() * 0.2);
			
			ball.setRadius(height() * 0.035);
			ball.setBaseVelocity(5);
			ball.setBaseVelocityModifier(0.015);

		} else if (gameOptions.getDifficulty() == Difficulty.Medium) {

			playerOne.setVelocity(800);
			playerTwo.setVelocity(800);
			
			playerOne.setAiSpeed(800 * 0.9);
			playerTwo.setAiSpeed(800 * 0.9);
			
			playerOne.setReactionTime(0.2);
			playerTwo.setReactionTime(0.2);
			
			playerOne.setErrorMultiplier(0.1);
			playerTwo.setErrorMultiplier(0.1);
	
			playerOne.setHeight(height() * 0.15);
			playerTwo.setHeight(height() * 0.15);
			
			ball.setRadius(height() * 0.025);
			ball.setBaseVelocity(6);
			ball.setBaseVelocityModifier(0.02);
			
		} else if (gameOptions.getDifficulty() == Difficulty.Hard) {
			
			playerOne.setVelocity(1000);
			playerTwo.setVelocity(1000);
			
			playerOne.setAiSpeed(1000 * 0.95);
			playerTwo.setAiSpeed(1000 * 0.95);
			
			playerOne.setReactionTime(0.15);
			playerTwo.setReactionTime(0.15);
			
			playerOne.setErrorMultiplier(0.05);
			playerTwo.setErrorMultiplier(0.05);
	
			playerOne.setHeight(height() * 0.1);
			playerTwo.setHeight(height() * 0.1);
			
			ball.setRadius(height() * 0.02);
			ball.setBaseVelocity(7);
			ball.setBaseVelocityModifier(0.025);
		}		
	}
	
}