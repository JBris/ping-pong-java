package src.assignment.system;

import java.awt.*;
import java.util.*;
import java.lang.*;
import src.assignment.state.*;

public class GraphicsManager implements InterfaceGraphicsManager {
	
	/***Properties***/
		
	protected InterfaceGame game;
	
	protected Map<String, Image> imageList;
	protected Map<String, Animation> animationList;
	
	/***Constructor***/
	
	public GraphicsManager(InterfaceGame game) {
		this.game = game;
		imageList = new HashMap<String, Image>();
		animationList = new HashMap<String, Animation>();
	}
	
	public void registerImage(String name, Image image) {
		imageList.put(name, image);
	}
	
	public Image getImage(String name) {
		Image image = imageList.get(name);
		if (image == null) {
			throw new NullPointerException(String.format("Image %s has not been registered.", name));
		} 
		return image;	
	}
	
	public Image[] buildAnimation(Image image, int width, int height, int numFrames, int numOfRows, int numOfColumns, int startingRow, int startingColumn) {
		Image[] animation = new Image[numFrames];
		for(int i = startingColumn; i < numFrames; i++) {
			int y = i / numOfRows;
			int x = i % numOfColumns;

			animation[i] = game.subImage(image, x* width, startingRow * height + y * height, width, height);
		}
		return animation;
	}
	
	public void registerAnimation(String name, Image[] frames, double duration) {
		Animation animation = new Animation(name, frames, frames.length, duration);
		animationList.put(name, animation);
	}

	public Animation getAnimation(String name) {
		Animation animation  = animationList.get(name);
		if (animation == null) {
			throw new NullPointerException(String.format("Animation %s has not been registered.", name));
		} 
		return animation;	
	}
	
	public void updateAnimationFrame(String name) {
		Animation animation = this.getAnimation(name);
		animation.currentFrame = this.getFrame(animation. duration, animation.numOfFrames);
	}
	
	public void playAnimation(String name, double x, double y, double w, double h) {
		Animation animation = this.getAnimation(name);
		game.drawImage(animation.frames[animation.currentFrame], x, y, w, h);
	}
	
	public void drawImage(String name, double x, double y, double w, double h) {
		Image image = getImage(name);
		game.drawImage(image, x, y, w, h);
	}
	
	public void changeColor(Color c) {
		game.changeColor(c);
	}

	public void changeBackgroundColor(Color c) {
		game.changeBackgroundColor(c);
	}
	
	public void drawText(double x, double y, String s, String font, int size) {
		game.drawText(x, y , s, font, size);
	}
	
	public void drawMenuText (int menuChoice, int menuItem, double width, double height, String text) {
		if (menuChoice == menuItem) {
			game.changeColor(Color.YELLOW);
		} else {
			game.changeColor(Color.WHITE);
		}
		
		game.drawText(width, height , text, "Arial", (int) (game.height() * 0.05));
	}	
	
	protected int getFrame(double duration, int numOfFrames) {
		return (int)Math.floor(((getTime()/1000.0 % duration) / duration) * numOfFrames);
	}
	
	protected long getTime() {
		return System.currentTimeMillis();
	}
	
}