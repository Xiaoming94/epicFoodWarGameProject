//Author: Jimmy

//A class that does things that only need to be done to the client.

package gamecomponent.controllerparts;

import gamecomponent.Model;

public class ControllerUtilClient implements IControllerUtil {

	@SuppressWarnings("unused") 
	private Model model = null;//ny
	
	
	
	@Override
	public void run() {
		
	}

	
	public void addModel(Model m){//ny
		this.model = m;
	}
}