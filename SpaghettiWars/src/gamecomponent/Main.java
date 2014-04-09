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
		
//		try {
//			new SpaghettiServer(54555, 54556);
//		try {
//			new SpaghettiServer(54555, 54777);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			new SpaghettiClient(54555, 54777, 5000, "127.0.0.1", "Säljägarn");
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//129.16.189.35 54555 54556
	}
	
}
