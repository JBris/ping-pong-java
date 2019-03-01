package src.assignment1.entity;

import java.awt.*;
import java.util.*;
import java.lang.*;
import src.assignment.entity.*;
import src.assignment.state.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public class Ball extends AbstractEntity implements InterfaceBall {
	
	/***Properties***/
	
	protected double radius;
	protected double baseVelocity, baseVelocityModifier, velocityModifer;

	protected InterfaceScoreWatcher scoreWatcher;
	 
	/***Constructor***/
	
	public Ball(InterfaceGame game,  InterfaceStateManager stateManager, 
	InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, InterfaceScoreWatcher scoreWatcher) {
		super(game, stateManager, graphicsManager, audioManager);
		this.scoreWatcher = scoreWatcher;
		radius = height() * 0.025;
 		baseVelocity = 5;
		baseVelocityModifier = 0.02;
	}
	
	/***Getters and Setters***/
	
	public double getRadius() { return radius; }
	public void setRadius(double r) { radius = r; }

	public double getBaseVelocity() { return baseVelocity; }
	public double getBaseVelocityModifier() { return baseVelocityModifier; }
	public void setBaseVelocity(double v) { baseVelocity = v; }
	public void setBaseVelocityModifier(double m) { baseVelocityModifier = m; }
	
	/***Methods***/
	
	public void reset() {
		double multipliedBaseVelocity = baseVelocity * 10;
		
		xCoordinate = width() / 2;
		yCoordinate = height() / 2;
			
		if (Math.random() < 0.5) {
			xVelocity = multipliedBaseVelocity;
		} else {
			xVelocity = multipliedBaseVelocity * -1;
		}
		
		if (Math.random() < 0.5) {
			yVelocity = multipliedBaseVelocity + rand(multipliedBaseVelocity) - multipliedBaseVelocity / 2;
		} else {
			yVelocity = (multipliedBaseVelocity + rand(multipliedBaseVelocity) - multipliedBaseVelocity / 2) * -1;
		}
	}
	
	public void update(double dt) {
		updateAnimationFrame("BallMovement");
	}
	
	public void collide() {
		if(yCoordinate < radius && yVelocity < 0) {
			yCoordinate = radius;
			wallBounce(radius);
		}

		if(yCoordinate > (height() -radius) && yVelocity > 0) {
			yCoordinate = height() -radius;
			wallBounce(height() -radius);
		}
		
		//Score
		if (xCoordinate >= width() + radius) { 
			playSound("score");
			reset();
			scoreWatcher.incrementScore(1);
		} else if ( xCoordinate <= -radius) {
			playSound("score");
			reset();
			scoreWatcher.incrementScore(2);
		}
		
	}
	
	public void collide(InterfacePaddle paddle) { 
		double paddleWidth = paddle.getWidth();
		double paddleHeight = paddle.getHeight();
		double paddleXPosition = paddle.getXCoordinate();
		double paddleYPosition = paddle.getYCoordinate();
		double paddleYVelocity = paddle.getYVelocity();

		double paddleLeft = paddleXPosition - paddleWidth / 2;
		double paddleRight = paddleXPosition + paddleWidth / 2;
		double paddleTop = paddleYPosition - paddleHeight / 2;
		double paddleBottom = paddleYPosition + paddleHeight / 2;
		double ballLeft = xCoordinate - radius;
		double ballRight = xCoordinate + radius;
		double ballTop = yCoordinate - radius;
		double ballBottom = yCoordinate + radius;
		
		double paddleBaseVelocity = paddle.getVelocity();
		double velocityModifer = paddleBaseVelocity * 0.1 + baseVelocity * baseVelocityModifier * 2;	
		//Without a velocity cap, the ball will eventually pass through the paddle.
		double velocityModiferCap = velocityModifer * 8;
		
		if (ballRight < paddleLeft) { return; }
		if (ballLeft > paddleRight) { return; }
		if (ballBottom < paddleTop) { return; }
		if (ballTop > paddleBottom) { return; }

		if(yCoordinate > paddleTop && yCoordinate < paddleBottom) {
			if (xVelocity > 0) { xCoordinate = paddleLeft - radius; }
			if (xVelocity < 0) { xCoordinate = paddleRight + radius; }
			paddleBounceX(velocityModifer, velocityModiferCap);	
			paddleBounceY(paddle, velocityModifer);						
		} else if(xCoordinate > paddleLeft && xCoordinate < paddleRight) {
			if (yCoordinate < paddleYPosition) { yCoordinate = paddleTop - radius; }
			if (yCoordinate > paddleYPosition) { yCoordinate = paddleBottom + radius; }
			paddleBounceY(paddle, velocityModifer);						
		} else {

			if(xCoordinate > paddleXPosition && xVelocity < 0) {
				xCoordinate = paddleRight + radius;
				paddleBounceX(velocityModifer, velocityModiferCap);	
			} else if(xCoordinate < paddleXPosition && xVelocity > 0) {
				xCoordinate = paddleLeft - radius;
				paddleBounceX(velocityModifer, velocityModiferCap);	
			}  else {
				if (xVelocity > 0 && xVelocity < velocityModiferCap) {
					xVelocity += velocityModifer;
				} else if (xVelocity < 0 && xVelocity > -velocityModiferCap) {
					xVelocity -= velocityModifer;
				}	
			}
			if(yCoordinate > paddleYPosition && yVelocity < 0) {
				yCoordinate = paddleBottom + radius;
				paddleBounceY(paddle, velocityModifer);										
			} else if(yCoordinate < paddleYPosition && yVelocity > 0) {
				yCoordinate = paddleTop - radius;
				paddleBounceY(paddle, velocityModifer);						
			} else {
				paddleBounceY(paddle, velocityModifer);						
			}
		}
		
		//Need to set x & y coordinates, as the ball can get stuck between walls & paddles
		if(yCoordinate < radius) {
			yCoordinate = paddleTop + radius;
			if (xCoordinate > width() / 2) { xCoordinate = paddleLeft - radius;  }
			if (xCoordinate < width() / 2) { xCoordinate = paddleRight + radius;  }
		}

		if(yCoordinate > (height() -radius)) {
			yCoordinate = paddleBottom -radius;
			if (xCoordinate > width() / 2) { xCoordinate = paddleLeft - radius;  }
			if (xCoordinate < width() / 2) { xCoordinate = paddleRight + radius;  }
		}
		playSound("beep");
	}
	
	protected void paddleBounceX(double velocityModifer, double velocityModiferCap) {
		xVelocity *= -1;
		if (xVelocity > 0 && xVelocity < velocityModiferCap) {
			xVelocity += velocityModifer;
		} else if (xVelocity < 0 && xVelocity > -velocityModiferCap) {
			xVelocity -= velocityModifer;
		}

	}
	
	protected void paddleBounceY(InterfacePaddle paddle, double velocityModifer) {
		double paddleHeight = paddle.getHeight();
		double paddleYPosition = paddle.getYCoordinate();
		double paddleYVelocity = paddle.getYVelocity();
		
		if (yCoordinate <  paddleYPosition - paddleHeight * 0.15) { 
			if (yVelocity > 0) { yVelocity *= -1; }
			//Reduce the effect of the paddle's y velocity to slow down the game.
			yVelocity += -velocityModifer + paddleYVelocity / 4; 
		} else if (yCoordinate >  paddleYPosition + paddleHeight * 0.15) { 
			if (yVelocity < 0) { yVelocity *= -1; }
			yVelocity += velocityModifer + paddleYVelocity / 4; 
		} 	
	}

	protected void wallBounce(double subtractValue) {
		double t = (yCoordinate - subtractValue) / yVelocity;
		yVelocity -= yVelocity * t;
		yVelocity *= -1;
		yCoordinate += yVelocity * t;
		playSound("beep");
	}
	
 	public void reset(double x, double y, double xVelocity, double yVelocity, double radius) {
		xCoordinate = x;
		yCoordinate = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.radius = radius;
	}
	
	public void render() {
		if(xVelocity < 0) {
			playAnimation("BallMovement", xCoordinate + radius * 3, yCoordinate - radius * 3, -radius * 6, radius * 6);
		} else {
			playAnimation("BallMovement", xCoordinate - radius * 3, yCoordinate - radius * 3, radius * 6, radius * 6);
		}
	}
}