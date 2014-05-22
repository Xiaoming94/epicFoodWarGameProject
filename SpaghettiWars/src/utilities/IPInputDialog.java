//Author: Henry

//A class that recieves and uses the IP when a person tries to join game.

package utilities;

import java.io.IOException;

import com.badlogic.gdx.Input;

import gamecomponent.Model;
import networking.NetworkUtils;

/**
 * Created by xiaoming on 29/04/14.
 */
public class IPInputDialog implements Input.TextInputListener {
    private final Model m;
    

    public IPInputDialog(Model m){
        this.m = m;
    }

    @Override
    public void input(String s) {
    	System.out.println("sending request");
        try {
			NetworkUtils.createClient(s,m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void canceled() {

        //Do Something Useful....

    }
}
