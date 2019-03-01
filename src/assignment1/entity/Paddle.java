package src.assignment1.entity;

import java.awt.*;
import java.util.*;
import java.lang.*;
import src.assignment.entity.*;
import src.assignment.state.*;
import src.assignment.system.*;

public class Paddle extends AbstractEntity implements InterfacePaddle {
	
	/***Properties***/
	
	protected double velocity;
	protected int score;
	protected double width, height;
	protected boolean up, down;
	
	protected boolean isAiEnabled;
	protected double aiSpeed, aiReactionTime, aiReactionTimer, aiErrorMultiplier;
	
	/***Constructor***/
	
	public Paddle(InterfaceGame game,  InterfaceStateManager stateManager, 
	InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager) {
		super(game, stateManager, graphicsManager, audioManager);
		
		velocity = 600;
		aiSpeed = velocity * 0.7;
		aiReactionTime = 0.4;
		aiErrorMultiplier = 0.15;
		width = width() * 0.03;
		height = height() * 0.2;
		reset(0);
	}
	
	/***Getters and Setters***/
	
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }

	public double getVelocity() { return velocity; }
	public void setVelocity(double v) { velocity = v; }
	
	public double getHeight() { return height; }
	public double getWidth() { return width; }
	public void setHeight(double h) { height = h; }
	public void setWidth(double w) { width = w; }
	
	public boolean getUp() { return up; }
	public boolean getDown() { return down; }
	public void setUp(boolean d) { up = d; }
	public void setDown(boolean d) { down = d; }
	
	public boolean isAiEnabled() { return isAiEnabled;}
	public void setAiEnabled(boolean aiEnabled) { isAiEnabled = aiEnabled; } 
	
	public double getAiSpeed() { return aiSpeed; }
	public double getReactionTime() { return aiReactionTime; }
	public double getReactionTimer() { return aiReactionTimer; }	
	public double getErrorMultiplier() { return aiErrorMultiplier; }
	public void setAiSpeed(double s) { aiSpeed = s; } 
	public void setReactionTime(double t) { aiReactionTime = t; }
	public void setReactionTimer(double t) { aiReactionTimer = t; }
	public void setErrorMultiplier(double m) { aiErrorMultiplier = m; } 
	
	/***Methods***/
	
	public void incrementScore() { score++; }

	public void reset(double x) {
		up = false;
		down = false;
		score = 0;
		isAiEnabled = false;
		aiReactionTimer = 0;

		xVelocity = 0;
		yVelocity = 0;
		
		xCoordinate = x;
		yCoordinate = height() / 2;
	}
	
	public void reset(double x, double y, double xVelocity, double yVelocity, double width, double height) {
		xCoordinate = x;
		yCoordinate = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.width = width;
		this.height = height;
		score = 0;
		isAiEnabled = false;
	}
	
	public void move() {
		if (yCoordinate < height / 2) {
			yCoordinate = height / 2;
			up = false;
			yVelocity = 0;
		} else if (yCoordinate > height() - height / 2) {
			yCoordinate = height() - height / 2;
			down = false;
			yVelocity = 0;
		}
		
		if (up == true) { yVelocity = -velocity; }
		if (down == true) { yVelocity = velocity; }
	}
	
	public void move(double dt) {
		yCoordinate += yVelocity * dt;
	}
	
	public void aiMove(double dt, double ballPositionX, double ballPositionY) {
		aiReactionTimer += dt;
		if (aiReactionTimer < aiReactionTime) { return; }
		aiReactionTimer -= aiReactionTime;
		xVelocity = ballPositionX - xCoordinate;
		yVelocity = ballPositionY - yCoordinate;
		double l = length(xVelocity, yVelocity);
		yVelocity *= aiSpeed/l;
		yVelocity *= 1 + (rand(aiErrorMultiplier * 2) - aiErrorMultiplier);
		
	}
	
	public void render() {
		changeColor(Color.WHITE);
		if (name == "1") {
			drawImage("Paddle1", xCoordinate - width / 2, yCoordinate - height / 2, width, height);	
		} else if (name == "2") {
			drawImage("Paddle2", xCoordinate - width / 2, yCoordinate - height / 2, width, height);
		} else {
			changeColor(Color.WHITE);
			drawSolidRectangle(xCoordinate - width / 2, yCoordinate - height / 2, width, height);		
		}
	}
	
	public void aiMove(double dt) {}
}