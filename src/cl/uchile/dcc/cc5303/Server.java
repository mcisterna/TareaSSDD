package cl.uchile.dcc.cc5303;


import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Game game;

    public Server(Game game) throws RemoteException {
        super();
        this.game = game;
    }

    public IPlayer joinGame() throws RemoteException {

        //Game is full
        if(game.players.size() == game.maxPlayers) return null;

        Player player = game.isAllTogether() || Player.playerCounter < 2 ?
                new Player(100 + Player.playerCounter*150, 50, 3) : new Player(400, 220, 3);

        game.addPlayer(player);
        return player;
    }


    @Override
    public IGame getGame() throws RemoteException {
        return game;
    }


    private final static String TITLE = "Juego - CC5303";
    private final static int WIDTH = 800, HEIGHT = 600;
    

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
        GameEngine gameEngine = new GameEngine(game);
        
	        while(true) {
	            if((!game.isAllTogether() && game.players.size() > 1) ||
	                    (game.isAllTogether()) && game.maxPlayers == game.players.size()) break;
	            Thread.sleep(1000);
	        }
	        gameEngine.runGame();
	        
    }

    static public Game createNormalGame() throws RemoteException {
        return new Game();
    }

    static public Game createAllTogetherGame(int numPlayers) throws RemoteException {
        return new Game(numPlayers);
    }


}
