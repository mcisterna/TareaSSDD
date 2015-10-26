package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.util.ArrayList;

/**
 * Created by jose on 10/21/15.
 */
public class ServersManager {

    private ArrayList<IServer> servers;

    private ServersManager() {
        this.servers = new ArrayList<>();
    }

    public void addServer(IServer server) {
        servers.add(server);
    }

    public IServer getLowestLoadServer() {
        IServer lowestLoadServer = null;
        double lowestLoad = 1;

        for(IServer server : servers) {
            if(server.getLoad() < lowestLoad) lowestLoadServer = server;
        }

        return  lowestLoadServer;
    }


}
