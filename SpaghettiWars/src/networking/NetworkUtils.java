//Author: Henry

//A factory for creating spaghetticlient and spaghettiserver.

package networking;

import gamecomponent.Model;

import java.io.IOException;

/**
 * Created by xiaoming on 29/04/14.
 */
public class NetworkUtils {

	public static SpaghettiFace createClient(Model model) throws IOException {
		return createClient("Localhost", model);
	}

	public static SpaghettiFace createClient(String iP, Model model) throws IOException {
		SpaghettiClient client = new SpaghettiClient(54555, 54777, 5000, iP,
				"Jocke", model, model.getOtherPlayers());
		client.start();
		return client;
	}

	public static SpaghettiFace createServer(Model model) throws IOException {
		SpaghettiServer server = new SpaghettiServer(54555, 54777, model,
				model.getOtherPlayers());
		server.start();
		return server;

	}
}
