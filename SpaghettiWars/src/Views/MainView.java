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
		showMenu();
	}
	public void showMenu(){
		if(ms == null){
			ms = new MenuScreen(this);
		}
		this.setScreen(ms);
	}
	public void startGame(){
		if (gs == null){
			gs = new GameScreen(model, this);
		}
		this.setScreen(gs);
	}

}
