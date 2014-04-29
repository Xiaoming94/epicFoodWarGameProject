package utilities;

import com.badlogic.gdx.Input;
import gamecomponent.Model;

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
        m.createClient(s);
    }

    @Override
    public void canceled() {

        //Do Something Useful....

    }
}
