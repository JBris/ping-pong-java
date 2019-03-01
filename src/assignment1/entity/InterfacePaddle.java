package src.assignment1.entity;

import java.util.*;
import java.lang.*;
import src.assignment.entity.*;

public interface InterfacePaddle extends InterfaceEntity, InterfaceScore, InterfaceAi, InterfaceDimensions {
	
	public double getVelocity();
	public void setVelocity(double v);
	
	public boolean getUp();
	public boolean getDown();
	public void setUp(boolean d);
	public void setDown(boolean d);
	
	public void reset(double x);
	public void reset(double x, double y, double xVelocity, double yVelocity, double width, double height);

	public void aiMove(double dt, double ballPositionX, double ballPositionY);	
}