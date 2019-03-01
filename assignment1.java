// Bristow, James, 11189075, Assignment 1, 159.103 
/* 
  * This program executes the game, Pong. 
  * It offers one player support against an AI opponent, demo mode, and two player support.
  * 
  * === Controls ===
  *
  * W - Player One Up
  * S - Player One Down
  * Up Arrow - Player Two Up; Menu Up
  * Down Arrow - Player Two Down; Menu Down
  * P - Pause
  * Enter - Confirm
  * Escape - Exit
  * 
  * ======
  *
  * See the src directory for the other class files.
  * Ball and paddle implementations are in src/assignment1/entity
  * Game states are in src/assignment1/state
  * System files are in src/assignment/system
  *
  * See the assets directory for images and audio.
  */ 
  
//"Warning" by Josh Penn-Pierson is licensed under CC BY 4.0; see https://github.com/OpenSourceMusic/Warning
//"Boss 1" by Josh Penn-Pierson is licensed under CC BY 4.0; see https://github.com/OpenSourceMusic/Boss-1
//"The Speed Consumes Me" by Josh Penn-Pierson is licensed under CC BY 4.0; see https://github.com/OpenSourceMusic/The-Speed-Consumes-Me
//"Pause Menu Track 1" by Josh Penn-Pierson is licensed under CC BY 4.0; see https://github.com/OpenSourceMusic/Pause-Menu-Track-1
//PennPierson.com

//"Astronomy Constellation Dark" by Francesco Ungaro is licensed under Pexels License; see https://www.pexels.com/photo/starry-sky-998641/
//"Astronomy Constellation Cosmos" by Sheena Wood is licensed under Pexels License;  https://www.pexels.com/photo/astronomy-constellation-cosmos-dark-574115/

//"Paddle1" by kenney.nl is licensed under CCO; see https://opengameart.org/content/sokoban-100-tiles
//"Paddle2" by kenney.nl is licensed under CCO; see https://opengameart.org/content/sokoban-100-tiles
//"Ball" by untiedgames is licensed under Will's Public License; see https://untiedgames.itch.io/floating-skull-enemy

//javac src/assignment/state/*java && javac src/assignment/system/*java && javac src/assignment/service/*java && javac src/assignment/entity/*java && javac *java
//javac src/assignment1/state/*java && javac src/assignment1/system/*java && javac src/assignment1/service/*java && javac src/assignment1/entity/*java && javac *java
//java assignment1

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import src.assignment.system.*;
import src.assignment.state.*;
import src.assignment.service.*;
import src.assignment.service.Container;
import src.assignment1.state.*;
import src.assignment1.service.*;

public class assignment1 extends GameEngine implements InterfaceGame {
	
	/***Properties***/
	
	protected InterfaceStateManager stateManager;
	protected InterfaceGraphicsManager graphicsManager;
	protected InterfaceAudioManager audioManager;
	
	private InterfaceContainer container;
	
	// Main Function
	public static void main(String args[]) {
		createGame(new assignment1(), 60);
	}
	
	/***Constructor***/
	
	public assignment1 () {
		super();
		boot(new ServiceContext(this));
	}
	
	/***Methods***/
	
	public void init() {
			setWindowSize(700, 700);
	}
	
	public void update(double dt) {
		stateManager.update(dt);
	}
	
	public void paintComponent() {
		changeBackgroundColor(black);
		clearBackground(width() + 30, height() + 30);
		stateManager.paintComponent();
	}
		
	public void drawDisplayLine(double x1, double y1, double x2, double y2, double l) {
		drawLine(x1, y1, x2, y2, l);
	}
	
	public void drawDisplayCircle(double x, double y, double r) {
		drawSolidCircle(x, y, r);
	}
	
	public void drawDisplayRectangle(double x, double y, double w, double h) {
		drawSolidRectangle(x, y, w, h);
	}
	
	public void keyPressed(KeyEvent e) {
		stateManager.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		stateManager.keyReleased(e);
	}
	
	public void mouseExited(MouseEvent e) {
		stateManager.mouseExited(e);
	}
	
	public void playAudio(AudioSource audioSource) {
		AudioClip audio = AudioClip.class.cast(audioSource.get());
		playAudio(audio);
	}
	
	public void startAudioLoop(AudioSource audioSource) {
		AudioClip audio = AudioClip.class.cast(audioSource.get());
		startAudioLoop(audio);
	}
	
	public void stopAudioLoop(AudioSource audioSource) {
		AudioClip audio = AudioClip.class.cast(audioSource.get());
		stopAudioLoop(audio);
	}
	
	protected void boot(InterfaceServiceContext serviceContext) {
		serviceContext.bindServices();
		container = serviceContext.getContainer();
		
		stateManager = container.getService(InterfaceStateManager.class);
		registerAllStates();
		
		graphicsManager = container.getService(InterfaceGraphicsManager.class);
		loadAllImages();
		
		audioManager = container.getService(InterfaceAudioManager.class);		
		registerAllAudio();
		
		stateManager.switchState(GameState.Menu.name());	
		stateManager.getCurrentState().reset();
	}
	
	protected void loadAllImages() {
		registerImage("Menu", "assets/image/astronomy-constellation-cosmos.jpg");
		registerImage("Play", "assets/image/astronomy-constellation-dark.jpg");
		registerImage("Paddle1", "assets/image/Paddle1.png");
		registerImage("Paddle2", "assets/image/Paddle2.png");

		Image[] ballMovement = graphicsManager.buildAnimation(loadImage("assets/image/Ball.png"), 168, 168, 16, 4, 4, 0, 0);
		graphicsManager.registerAnimation("BallMovement", ballMovement, 1);
	}
		
	protected void registerImage(String name, String path) {
		graphicsManager.registerImage(name, loadImage(path));
	}
	
	protected void registerAllAudio() {
		registerAudio("beep", "assets/audio/beep.wav");
		registerAudio("score", "assets/audio/score.wav");
		registerAudio("Menu", "assets/audio/Pause Menu Track 1 v1.0.wav");
		registerAudio("Boss", "assets/audio/Boss 1 v1.0.wav");
		registerAudio("Warning", "assets/audio/Warning v1.0.wav");
		registerAudio("Speed", "assets/audio/The Speed Consumes Me v1.0.wav");
		
		audioManager.addToPlayList("Boss");
		audioManager.addToPlayList("Warning");
		audioManager.addToPlayList("Speed");
		audioManager.shufflePlayList();
	}
	
	protected void registerAudio(String audioName, String audioPath) {
		AudioClip audio = loadAudio(audioPath);	
		AudioSource audioSource =  new AudioSource<AudioClip>(audioName, audio);
		audioManager.registerAudio(audioSource);
	}
	
	protected void registerAllStates() {
		InterfaceStateFactory stateFactory = container.getService(InterfaceStateFactory.class);
		
		InterfaceState menuState = stateFactory.createInstance(GameState.Menu.name());
		stateManager.registerState(menuState);
		
		InterfaceState optionsState = stateFactory.createInstance(GameState.Options.name());
		stateManager.registerState(optionsState);	
		
		InterfaceState constrolsState = stateFactory.createInstance(GameState.Controls.name());
		stateManager.registerState(constrolsState);
		
		InterfaceState playSingleState = stateFactory.createInstance(GameState.PlaySingle.name());
		stateManager.registerState(playSingleState);
		
		InterfaceState playMultiState = stateFactory.createInstance(GameState.PlayMulti.name());
		stateManager.registerState(playMultiState);
		
		InterfaceState demoState = stateFactory.createInstance(GameState.Demo.name());
		stateManager.registerState(demoState);			
	}
}