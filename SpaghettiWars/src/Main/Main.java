/*
 * Author: Jimmy Eliasson malmer
 */

//Class containing the main method.

package Main;

import gamecomponent.Model;
import gamecomponent.views.MainView;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		new MainView(model);
	}

}
