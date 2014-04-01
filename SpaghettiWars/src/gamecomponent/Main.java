/*
 * Author: Jimmy Eliasson malmer
 */


package gamecomponent;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		new View(model);
		
		//wait for View to load textures before controller try to create player
		try{
		    Thread.sleep(1500);
		}catch(InterruptedException e){
		    System.out.println("got interrupted!");
		}
		
		Thread ct = new Thread(new Controller(model));
		ct.start();
		
	}
	
}
