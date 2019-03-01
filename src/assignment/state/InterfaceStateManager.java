package src.assignment.state;

import java.util.*;
import java.lang.*;
import src.assignment.system.*;
import src.assignment.service.*;

public interface InterfaceStateManager extends InterfaceKeyListener, InterfaceMouseListener, InterfaceService {
	
	public void registerState(InterfaceState state);
	
	public InterfaceState getCurrentState();

	public void switchState(String stateName);
	
	public void update(double dt);
	
	public void paintComponent();
	
}