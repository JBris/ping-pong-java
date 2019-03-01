package src.assignment1.state;

import java.util.*;
import java.lang.*;
import src.assignment1.entity.*;
import src.assignment.service.*;
import src.assignment.state.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public class StateFactory extends AbstractStateFactory {
	
	/***Properties***/
	
	InterfaceGameOptions gameOptions;
	InterfaceGameEntityFactory gameEntityFactory;
	
	/***Constructor***/

	public StateFactory(InterfaceGame game, InterfaceContainer container, InterfaceStateManager stateManager, 
		InterfaceGraphicsManager graphicsManager, InterfaceAudioManager audioManager, 
		InterfaceGameOptions gameOptions, InterfaceGameEntityFactory gameEntityFactory) {
		super(game, container, stateManager, graphicsManager, audioManager);
		this.gameOptions = gameOptions;
		this.gameEntityFactory = gameEntityFactory;
	}
	
	/***Methods***/

	public InterfaceState createInstance (String stateName) {
		
		if (stateName == GameState.Menu.name()) {
			return createMenuState();	
		} else if (stateName == GameState.Options.name()) {
			return createOptionsState();
		} else if (stateName == GameState.Controls.name()) {
			return createControlsState();
		} else if (stateName == GameState.PlaySingle.name()) {
			return createPlaySingleState();
		} else if (stateName == GameState.PlayMulti.name()) {
			return createPlayMultiState();
		} else if (stateName == GameState.Demo.name()) {
			return createDemoState();
		}
		
		return createBlankState();
		
	}
	
	public InterfaceState createBlankState() {
		return new BlankState();
	}
	
	protected InterfaceState createMenuState() {
		return new MenuState(game, stateManager, graphicsManager, audioManager);
	}
	
	protected InterfaceState createOptionsState() {
		return new OptionsState(game, stateManager, graphicsManager, audioManager, gameOptions);
	}
	
	protected InterfaceState createControlsState() {
		return new ControlsState(game, stateManager, graphicsManager, audioManager);
	}
	
	protected InterfaceState createPlaySingleState() {
		return new PlaySingleState(game, stateManager, graphicsManager, audioManager, gameOptions, gameEntityFactory);
	}
	
	protected InterfaceState createPlayMultiState() {
		return new PlayMultiState(game, stateManager, graphicsManager, audioManager, gameOptions, gameEntityFactory);
	}
	
	protected InterfaceState createDemoState() {
		return new DemoState(game, stateManager, graphicsManager, audioManager, gameOptions, gameEntityFactory);
	}
	
}