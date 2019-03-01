package src.assignment.entity;

import java.awt.*;
import java.util.*;
import java.lang.*;
import src.assignment.state.*;
import src.assignment.system.*;

public abstract class AbstractEntity implements InterfaceEntity {
	
	/***Properties***/
	
	InterfaceGame game;
	
	protected InterfaceStateManager stateManager;
	protected InterfaceGraphicsManager graphicsManager;
	protected InterfaceAudioManager audioManager;
	
	protected String name;
	protected double xCoordinate, yCoordinate;
	protected double xVelocity, yVelocity;
	
	/***Constructor***/
	
	public AbstractEntity(InterfaceGame game,  InterfaceStateManager stateManager, 
	InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager) {
		this.game = game;
		this.stateManager = stateManager;
		this.graphicsManager = graphicsManager;
		this.audioManager = audioManager;
	}
	
	/***Getters and Setters***/
	
	public  String name() { return name; }
	
	public void setName(String name) { this.name = name; }

	public double getXCoordinate() { return xCoordinate; }
	
	public double getYCoordinate() { return yCoordinate; }
	
	public void setXCoordinate(double x) { xCoordinate = x; }
	
	public void setYCoordinate(double y) { yCoordinate = y; }
	
	public double getXVelocity() { return xVelocity; }
	
	public double getYVelocity() { return yVelocity; }
	
	public void setXVelocity(double x) { xVelocity = x; }
	
	public void setYVelocity(double y) { yVelocity = y; }
	
	/***Methods***/

	public void move(double dt) {
		xCoordinate += xVelocity * dt;
		yCoordinate += yVelocity * dt;
	}
	
	public void move() {}
	
	public double rand(double max) {
		return game.rand(max);
	}
	
	public int rand(int max) {
		return game.rand(max);
	}
	
	public void stop() {
		xVelocity = 0;
		yVelocity = 0;
	}
	
	protected void drawText(double x, double y, String s, String font, int size) {
		graphicsManager.drawText(x, y, s, font, size);
	}
	
	protected void drawLine(double x1, double y1, double x2, double y2, double l) {
		game.drawDisplayLine(x1, y1, x2, y2, l);
	}
	
	public void playSound(String soundName) {
		audioManager.playAudio(soundName);
	}
	
	protected void changeColor(Color c) {
		graphicsManager.changeColor(c);
	}
	
	public void collide() {}
	
	public void reset() {}
	
	public void update(double dt) {}

	public abstract void render();

	protected void drawSolidCircle(double x, double y, double r) {
		game.drawDisplayCircle(x, y, r);
	}
	
	protected void drawSolidRectangle(double x, double y, double w, double h) {
		game.drawDisplayRectangle(x, y , w, h);
	}
	
	protected void updateAnimationFrame(String name) {
		graphicsManager.updateAnimationFrame(name);
	}
	
	protected void playAnimation(String name, double x, double y, double w, double h) {
		graphicsManager.playAnimation(name, x, y, w, h);
	}

	protected void drawImage(String name, double x, double y, double w, double h) {
		graphicsManager.drawImage(name, x, y, w, h);
	}
	
	protected double height() {
		return game.height();
	}
	
	protected double width() {
		return game.width();
	}
	
	protected double length(double x, double y) {
		return game.length(x, y);
	}
}