package cl.uchile.dcc.cc5303;


import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer {

    private Game game;

    public Server(Game game) throws RemoteException {
        super();
        this.game = game;
    }

    public IPlayer joinGame() throws RemoteException {

        //Game is full
        if(game.players.size() == 4) return null;

        Player player = game.isAllTogether() || Player.playerCounter == 0 ?
                new Player(Player.playerCounter*150, 50, 3) : new Player(400, 0, 3);

        game.addPlayer(player);
        return player;
    }


    @Override
    public IGame getGame() throws RemoteException {
        return game;
    }


    private final static String TITLE = "Juego - CC5303";
    private final static int WIDTH = 800, HEIGHT = 600;
    public static String url = "rmi://localhost:1099/game";

    public static void main(String[] args) throws RemoteException, MalformedURLException {

        Game game;
        if(args.length > 3) {
            game = createAllTogetherGame(3);
        }
        else{
            game = createNormalGame();
        }


        IServer server = new Server(game);
        Naming.rebind(url, server);
        GameEngine gameEngine = new GameEngine(game);
        gameEngine.runGame();
    }

    static public Game createNormalGame() throws RemoteException {
        return new Game();
    }

    static public Game createAllTogetherGame(int numPlayers) throws RemoteException {
        return new Game(true, numPlayers);
    }


}
