package src.assignment1.entity;

import java.util.*;
import java.lang.*;
import src.assignment.entity.*;
import src.assignment.service.*;
import src.assignment.state.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public class GameEntityFactory implements InterfaceGameEntityFactory {
	
	/***Properties***/
	
	protected InterfaceGame game;
	protected InterfaceContainer container;
	
	protected InterfaceStateManager stateManager;
	protected InterfaceGraphicsManager graphicsManager;
	protected InterfaceAudioManager audioManager;
	
	InterfaceGameOptions gameOptions;
	
	/***Constructor***/

	public GameEntityFactory(InterfaceGame game, InterfaceContainer container, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, InterfaceGameOptions gameOptions) {
		this.game = game;
		this.container  = container;
		this.stateManager = stateManager;
		this.graphicsManager = graphicsManager;
		this.audioManager = audioManager;		
		this.gameOptions = gameOptions;
	}
	
	/***Methods***/
	
	public InterfaceBall createBall(InterfaceScoreWatcher scoreWatcher) {
		return new Ball(game, stateManager,graphicsManager, audioManager, scoreWatcher);
	}
	
	public InterfaceBall createBall(InterfaceScoreWatcher scoreWatcher, double x, double y, double xVelocity, double yVelocity, double radius) {
		InterfaceBall ball = new Ball(game, stateManager, graphicsManager, audioManager, scoreWatcher);
		ball.reset(x, y, xVelocity, yVelocity, radius);
		return ball;
	}
	
	public InterfacePaddle createPaddle() {
		return new Paddle(game, stateManager,graphicsManager, audioManager);	
	}
	
	public InterfacePaddle createPaddle(double x, double y, double xVelocity, double yVelocity, double width, double height)  {
		InterfacePaddle paddle = new Paddle(game, stateManager,graphicsManager, audioManager);
		paddle.reset(x, y, xVelocity, yVelocity, width, height);
		return paddle;
	}	

}