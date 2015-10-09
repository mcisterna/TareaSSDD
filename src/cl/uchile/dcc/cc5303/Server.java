package cl.uchile.dcc.cc5303;


import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {

    
	public Game game;

    public Server(Game game) throws RemoteException {
        super();
        this.game = game;
    }

    public IPlayer joinGame() throws RemoteException {

        //Game is full
        if(game.players.size() == game.maxPlayers) return null;

        Player player = game.isAllTogether() || Player.playerCounter < 2 ?
                new Player(100 + Player.playerCounter*150, 50, 1) : new Player(400, 220, 1);

        game.addPlayer(player);
        return player;
    }


    @Override
    public IGame getGame() throws RemoteException {
        return game;
    }


    private final static String TITLE = "Juego - CC5303";
    private final static int WIDTH = 800, HEIGHT = 600;
    public static String url = "rmi://172.17.69.208:1099/game";

    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException {
    	System.setProperty("java.rmi.server.hostname","172.17.69.208");
        Game game;
        System.out.println("Starting server...");
        if(args.length > 1) {
            game = createAllTogetherGame(Integer.parseInt(args[1]));
        }
        else{
            game = createNormalGame();
        }


        Server server = new Server(game);
        Naming.rebind(url, server);
        GameEngine gameEngine = new GameEngine(game);
        
        while(true){
	        while(true) {
	            if((!game.isAllTogether() && game.players.size() > 1) ||
	                    (game.isAllTogether()) && game.maxPlayers == game.players.size()) break;
	            Thread.sleep(1000);
	        }
	        gameEngine.runGame();
	        
	        if(args.length > 1) {
	            game = createAllTogetherGame(Integer.parseInt(args[1]));
	        }
	        else{
	            game = createNormalGame();
	        }
	        server.game = game;
	        
        }
    }

    static public Game createNormalGame() throws RemoteException {
        return new Game();
    }

    static public Game createAllTogetherGame(int numPlayers) throws RemoteException {
        return new Game(numPlayers);
    }


}
