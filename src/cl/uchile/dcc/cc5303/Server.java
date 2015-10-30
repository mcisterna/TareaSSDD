package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.elements.Bench;
import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IBench;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.ILevel;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;
import cl.uchile.dcc.cc5303.interfaces.IServersManager;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer {

	public Game game;
    public int playersWaiting;
	private boolean isRunning;
	public GameEngine gameEngine;


    public Server() throws RemoteException {
        super();
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
        this.gameEngine = gameEngine;
        gameEngine.runGame();
    }
    
    @Override
	public void runNewGame(String[] args) throws RemoteException {
		if(args.length > 2) {
            game = createAllTogetherGame(Integer.parseInt(args[2]));
        }
        else{
            game = createNormalGame();
        }
		isRunning = true;
		System.out.println("first server");

		
	}

	@Override
	public void resumeGame(IGame game) throws RemoteException {
		this.game = createGame(game);
		isRunning = true;
		System.out.println("im starting");
		
	}
	
	private Game createGame(IGame game) throws RemoteException {
		Game newgame = new Game();
		
		for(IPlayer player : game.getPlayers()) {
			newgame.players.add(new Player(
					player.getLeft(),
					player.getTop(),
					player.getHeight(),
					player.getWidth(),
					player.getLives(),
					player.getPlayerCounter(),
					player.getColor()));
		}
		
		newgame.levels = new LinkedList<Level>();
	    for(ILevel l: game.getLevels()){
	    	LinkedList<Bench> benches = new LinkedList<Bench>();
	    	for(IBench b : l.getBenches()){
	    		benches.add(new Bench(b.getLeft(),b.getTop(),b.getWidth(),b.getHeight()));
	    	}
	    	newgame.levels.add(new Level(l.getId(),l.getStaticId(),benches));
	    }
	    newgame.numPlayers = game.getNumPlayers();
	    newgame.allTogether = game.getAllTogether();
	    newgame.maxPlayers = game.getMaxPlayers();
	    

	    for(IPlayer player : game.getRanking()) {
			newgame.ranking.add(new Player(
					player.getLeft(),
					player.getTop(),
					player.getHeight(),
					player.getWidth(),
					player.getLives(),
					player.getPlayerCounter(),
					player.getColor()));
		}

		return newgame;
	}

	public Game createNormalGame() throws RemoteException {
        return new Game();
    }

    public Game createAllTogetherGame(int numPlayers) throws RemoteException {
        return new Game(numPlayers);
    }

	@Override
	public void stopGame() throws RemoteException {
		isRunning = false;
		gameEngine.stop();
		System.out.println("im stoping");
		
	}

    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException, NotBoundException {
    	if(args.length == 0){
        	System.out.println("Debe ingresar ip");
        	return;
        }
    	String ip = args[0];
    	String url = "rmi://"+ip+":1099/game";
        Game game;
        System.out.println("Starting server...");
        
        Server server = new Server();
        IServersManager serversManager = (IServersManager) Naming.lookup(url);
        serversManager.addServer(server);


        while(true) {
        	if(server.isRunning){
	            server.waitUntilGameIsReadyToStart();
	            server.startGame();
	            if(server.gameEngine.isRunning()) {
	                server.waitForAllPlayers();
	                server.restartGame();
	            }
        	}
        	Thread.sleep(1000);
        }


    }


}
