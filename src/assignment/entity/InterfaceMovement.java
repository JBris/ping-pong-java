package src.assignment.entity;

import java.util.*;
import java.lang.*;

public interface InterfaceMovement {
	
	public double getXVelocity();
	public double getYVelocity();
	public void setXVelocity(double x);
	public void setYVelocity(double y);
	public void move(double dt);
	public void move();
	public void stop();
}