/*
 * Author: Jimmy Eliasson malmer
 */


package gamecomponent;

import java.io.IOException;
import networking.SpaghettiClient;
import networking.SpaghettiServer;

public class Main{

	public static void main(String[] args){
		Model model = new Model();
		View view = new View(model);
		Thread ct = new Thread(new Controller(model, view));
		ct.start();
		model.createClient();
		
//		try {
//			new SpaghettiServer(54555, 54556, model);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	
//		try {
//			new SpaghettiClient(54555, 54777, 5000, "46.239.119.78", "Säljägarn", model);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//46.239.119.78 54555 54777
	}
		
	
	

}
