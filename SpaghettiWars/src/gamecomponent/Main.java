/*
 * Author: Jimmy Eliasson malmer
 */


package gamecomponent;

import java.io.IOException;

import networking.SpaghettiServer;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		View view = new View(model);
		Thread ct = new Thread(new Controller(model, view));
		ct.start();
		
		try {
			new SpaghettiServer(54555, 54556);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
