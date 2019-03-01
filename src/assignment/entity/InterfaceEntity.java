package src.assignment.entity;

import java.util.*;
import java.lang.*;

public interface InterfaceEntity extends InterfaceCollide, InterfaceMovement, InterfacePosition, InterfaceRenderable, InterfaceSound {
	
	public String name();
	public void setName(String name);
	public void reset();
	public void update(double dt);
}