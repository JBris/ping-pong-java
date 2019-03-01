package src.assignment.system;

import java.awt.*;
import java.util.*;
import java.lang.*;

public interface InterfaceGraphicsManager {
	
	public void registerImage(String name, Image image);
	
	public Image getImage(String name);
	
	public Image[] buildAnimation(Image image, int width, int height, int numFrames, int numOfRows, int numOfColumns, int startingRow, int startingColumn);
	public void registerAnimation(String name, Image[] animation, double duration);
	public Animation getAnimation(String name);
	public void updateAnimationFrame(String name);
	public void playAnimation(String name, double x, double y, double w, double h);
	
	public void drawImage(String name, double x, double y, double w, double h);
	
	public void changeColor(Color c);
	
	public void changeBackgroundColor(Color c);
	
	public void drawText(double x, double y, String s, String font, int size);

	public void drawMenuText (int menuChoice, int menuItem, double width, double height, String text);	
		
}