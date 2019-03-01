package src.assignment1.entity;

import java.util.*;
import java.lang.*;
import src.assignment.entity.*;
import src.assignment1.system.*;

public interface InterfaceGameEntityFactory {
	
	public InterfaceBall createBall(InterfaceScoreWatcher scoreWatcher);
	public InterfaceBall createBall(InterfaceScoreWatcher scoreWatcher, double x, double y, double xVelocity, double yVelocity, double radius);
	public InterfacePaddle createPaddle();
	public InterfacePaddle createPaddle(double x, double y, double xVelocity, double yVelocity, double width, double height);
	
}