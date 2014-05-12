package utilities;

import java.io.IOException;

import com.badlogic.gdx.Input;

import gamecomponent.Model;
import gamecomponent.views.MainView;
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
