package src.assignment1.entity;

import java.util.*;
import java.lang.*;
import src.assignment.entity.*;

public interface InterfaceBall extends InterfaceEntity, InterfaceRadius, InterfaceBallCollision {
	
	public void reset(double x, double y, double xVelocity, double yVelocity, double radius);
	
	public double getBaseVelocity();
	public double getBaseVelocityModifier();
	public void setBaseVelocity(double v);
	public void setBaseVelocityModifier(double m);
	
}