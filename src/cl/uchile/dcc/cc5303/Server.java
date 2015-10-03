package cl.uchile.dcc.cc5303;


import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer {

    List<Player> players;
    boolean todosJuntos;
    private Game game;

    public Server(List<Player> players, boolean todosJuntos, Game game) throws RemoteException {
        super();
        this.players = players;
        this.todosJuntos = todosJuntos;
        this.game = game;
    }

    public IPlayer joinGame() throws RemoteException {

        //Game is full
        if(players.size() == 4) return null;

        Player player = todosJuntos || Player.playerCounter == 0 ?
                new Player(Player.playerCounter*150, 50, 3) : new Player(400, 0, 3);

        players.add(player);
        return player;
    }

    @Override
    public IGame getGame() throws RemoteException {
        return game;
    }
}
