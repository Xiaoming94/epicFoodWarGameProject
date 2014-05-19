/*
 * Author: Jimmy Eliasson malmer
 */


package gamecomponent;

import gamecomponent.views.MainView;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		new MainView(model);
	}

}
