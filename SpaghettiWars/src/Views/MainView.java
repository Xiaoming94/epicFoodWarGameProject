package Views;

import gamecomponent.Model;

import com.badlogic.gdx.Game;

public class MainView extends Game {
	
	private Model model;
	private MenuScreen ms;
	private GameScreen gs;
	
	public MainView(Model m){
		model = m;
	}

	@Override
	public void create() {
		ms = new MenuScreen(this);
		gs = new GameScreen(model,this);
		this.setScreen(ms);

	}
	public void startGame(){
		
	}

}
