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
    private final static double MAX_LOAD = 0.75;
    public IServer currentServer;

    private ServersManager() throws RemoteException {
        super();
        this.servers = new ArrayList<IServer>();
    }

    @Override
    public void addServer(IServer server) throws RemoteException{
        servers.add(server);
    }

    public IServer getLowestLoadServer() throws RemoteException{
        IServer lowestLoadServer = null;
        double lowestLoad = 5;

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
        System.out.println("Starting Server Manager...");

        ServersManager serversManager = new ServersManager();
        Naming.rebind(url, serversManager);
        while(serversManager.currentServer == null){
        	serversManager.currentServer = serversManager.getLowestLoadServer();
        	Thread.sleep(1000);
        }
        serversManager.currentServer.runNewGame(args);

        while(true) {
        	System.out.println(serversManager.currentServer.getLoad());
            if(serversManager.currentServer.getGame().getPlayers().size() > 0 && serversManager.currentServer.getGame().getPlayers().get(0).isWantsToMoveLeft()) {
            	serversManager.currentServer.stopGame();
                IServer newServer = serversManager.getLowestLoadServer();
                newServer.resumeGame(serversManager.currentServer.getGame());
                serversManager.currentServer = newServer;
                }

            Thread.sleep(1000);
        }
    }

	@Override
	public IServer getCurrentServer() throws RemoteException {
		
		return currentServer;
	}


}
