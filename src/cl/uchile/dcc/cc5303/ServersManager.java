package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IServer;
import cl.uchile.dcc.cc5303.interfaces.IServersManager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServersManager extends UnicastRemoteObject implements IServersManager {

    private ArrayList<IServer> servers;
    private static double MAX_LOAD = 0.75;

    private ServersManager() throws RemoteException {
        super();
        this.servers = new ArrayList<>();
    }

    public void addServer(IServer server) {
        servers.add(server);
    }

    public IServer getLowestLoadServer() throws RemoteException {
        IServer lowestLoadServer = null;
        double lowestLoad = 1;

        for(IServer server : servers) {
            if(server.getLoad() < lowestLoad) lowestLoadServer = server;
        }

        return  lowestLoadServer;
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException {
        if(args.length == 0){
            System.out.println("Debe ingresar ip");
            return;
        }
        String ip = args[0];
        System.setProperty("java.rmi.server.hostname",ip);
        String url = "rmi://"+ip+":1099/game";
        Game game;
        System.out.println("Starting server...");
        if(args.length > 2) {
            game = createAllTogetherGame(Integer.parseInt(args[2]));
        }
        else{
            game = createNormalGame();
        }

        ServersManager serversManager = new ServersManager();
        Naming.rebind(url, serversManager);

        IServer currentServer = serversManager.getLowestLoadServer();
        currentServer.startGame(game);

        while(true) {
            if(currentServer.getLoad() > ServersManager.MAX_LOAD) {
                currentServer.stopGame();
                IServer newServer = serversManager.getLowestLoadServer();
                newServer.startGame(currentServer.getGame());
                }

            Thread.sleep(1000);
        }
    }

    static public Game createNormalGame() throws RemoteException {
        return new Game();
    }

    static public Game createAllTogetherGame(int numPlayers) throws RemoteException {
        return new Game(numPlayers);
    }


}
