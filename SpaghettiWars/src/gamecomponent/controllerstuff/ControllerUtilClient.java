package gamecomponent.controllerstuff;

import gamecomponent.Model;

public class ControllerUtilClient implements IControllerUtil {

	private Model model = null;//ny
	
	
	
	@Override
	//public void run(){
	public void run() {
		
	}

	
	public void addModel(Model m){//ny
		this.model = m;
	}
}