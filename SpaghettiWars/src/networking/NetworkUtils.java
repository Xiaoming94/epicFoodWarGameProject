package networking;

import gamecomponent.Model;

import java.io.IOException;

/**
 * Created by xiaoming on 29/04/14.
 */
public class NetworkUtils {
    public static void createClient(Model model){
        createClient("Localhost", model);
    }
    public static void createClient(String iP, Model model){
        try {
            SpaghettiClient client = new SpaghettiClient(54555, 54777, 5000, iP, "Jocke", model, model.getOtherPlayers(), model.getUnsentProjectiles());
            client.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void createServer(Model model){
        try {
            SpaghettiServer server = new SpaghettiServer(54555, 54777, model, model.getOtherPlayers(), model.getUnsentProjectiles());
            server.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
             e.printStackTrace();
        }


    }
}
