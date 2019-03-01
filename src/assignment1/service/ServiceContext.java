package src.assignment1.service;

import java.util.*;
import java.lang.*;
import src.assignment1.entity.*;
import src.assignment.state.*;
import src.assignment1.state.*;
import src.assignment.service.*;
import src.assignment.system.*;
import src.assignment1.system.*;

public class ServiceContext extends AbstractServiceContext {

	/***Constructor***/
	
	public ServiceContext (InterfaceGame game) {
		super(game);
	}
	
	/***Methods***/
	
	public void bindServices() {
		super.bindServices();
		
		InterfaceGame game = container.getService(InterfaceGame.class);
		InterfaceStateManager stateManager = container.getService(InterfaceStateManager.class);
		InterfaceGraphicsManager graphicsManager = container.getService(InterfaceGraphicsManager.class);
		InterfaceAudioManager audioManager = container.getService(InterfaceAudioManager.class);
		
		InterfaceGameOptions gameOptions =	 new GameOptions();
		container.bind(InterfaceGameOptions.class, gameOptions);

		InterfaceGameEntityFactory gameEntityFactory = new GameEntityFactory(game, container, stateManager, graphicsManager, audioManager, gameOptions);
		container.bind(InterfaceGameEntityFactory.class, gameEntityFactory);
		
		InterfaceStateFactory stateFactory = new StateFactory(game, container, stateManager, graphicsManager, audioManager, gameOptions, gameEntityFactory);
		container.bind(InterfaceStateFactory.class, stateFactory);
		
	}
	
}