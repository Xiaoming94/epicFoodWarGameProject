/*
 * Author: Jimmy Eliasson malmer
 */


package gamecomponent;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		new View(model);
		Thread ct = new Thread(new Controller(model));
		ct.start();
		
	}
	
}
