package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {

	public Game game;
    public int playersWaiting;


    public Server(Game game) throws RemoteException {
        super();
        this.game = game;
    }

    public IPlayer joinGame() throws RemoteException {

        //Game is full
        if(game.numPlayers == game.maxPlayers) return null;

        Player newPlayer = game.addPlayer();
        return newPlayer;
    }

    public void restartGame() throws RemoteException {
        this.game.restart();
    }

    @Override
    public IGame getGame() throws RemoteException {
        return game;
    }

    @Override
    synchronized public void playerIsReadyToContinue() throws RemoteException {
        playersWaiting--;
    }

    @Override
    public double getLoad() throws RemoteException {
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
    }

    public void waitForAllPlayers() throws RemoteException, InterruptedException {
        this.playersWaiting = this.game.numPlayers;
        while(playersWaiting != 0) Thread.sleep(1000);
    }

    private void waitUntilGameIsReadyToStart() throws InterruptedException {
        while(true) {
            if((!this.game.isAllTogether() && this.game.players.size() > 1) ||
                    (this.game.isAllTogether()) && this.game.maxPlayers == this.game.players.size()) break;
            Thread.sleep(1000);
        }
    }

    private void startGame() throws RemoteException {
        GameEngine gameEngine = new GameEngine(this.game);
        gameEngine.runGame();
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

        Server server = new Server(game);
        Naming.rebind(url, server);


        //Esto en otro thread?
        while(true) {
            server.waitUntilGameIsReadyToStart();
            server.startGame();
            if(elJuegoNoTerminoPorStop) {
                server.waitForAllPlayers();
                server.restartGame();
            }
            else {
                break;
            }
        }
        //Hasta aca

        //Desde aca ir chequeando si el servidor esta sobrecargado
        //Si lo esta, stopear el juego, pedir el servidor con menos carga al server manager,
        //enviar Game al nuevo servidor, y enviar a los clientes la nueva IP

    }


}
