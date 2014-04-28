/*
 * Author: Jimmy Eliasson malmer
 */


package gamecomponent;

import java.io.IOException;

import gamecomponent.views.MainView;
import networking.SpaghettiClient;
import networking.SpaghettiServer;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		MainView view = new MainView(model);
		Thread ct = new Thread(new Controller(model, view));
		ct.start();

		//wait for stuff to be created before we start networking.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//model.createServer();
		model.createClient();
	}




}
