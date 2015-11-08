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
        double lowestLoad = Integer.MAX_VALUE;

        for(IServer server : servers) {
            if(server.getLoad() < lowestLoad){
                lowestLoad = server.getLoad();
                lowestLoadServer = server;
            }
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
        String url = "rmi://"+ip+":1099/servers_manager";
        System.out.println("Servers Manager started.");

        ServersManager serversManager = new ServersManager();
        Naming.rebind(url, serversManager);
        while(serversManager.currentServer == null){
        	serversManager.currentServer = serversManager.getLowestLoadServer();
        	Thread.sleep(1000);
        }
        serversManager.currentServer.runNewGame(args);

        while(true) {
        	System.out.println("Current load: " + serversManager.currentServer.getLoad());
            if((serversManager.currentServer.getLoad() > MAX_LOAD || serversManager.currentServer.hasFreeSlot()) &&
                    serversManager.servers.size() > 1) {
            	serversManager.currentServer.setFreeSlot(false);
            	System.out.println("Migrating game.");
                serversManager.currentServer.stopGame();
                IServer newServer = serversManager.getLowestLoadServer();
                newServer.resumeGame(serversManager.currentServer.getGame());
                serversManager.currentServer = newServer;
            }

            Thread.sleep(1000);
        }
    }

	@Override
	public String getCurrentServerIp() throws RemoteException {
		
		return currentServer.getIp();
	}


}
