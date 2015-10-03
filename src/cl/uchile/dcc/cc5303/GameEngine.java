package cl.uchile.dcc.cc5303;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GameEngine extends UnicastRemoteObject implements IGameEngine{

    List<Player> players;
    boolean todosJuntos;

    public GameEngine(List<Player> players, boolean todosJuntos) throws RemoteException {
        super();
        this.players = players;
        this.todosJuntos = todosJuntos;
    }


    public IPlayer joinGame() throws RemoteException {

        Player player;

        if(todosJuntos || Player.playerCounter == 0) {
            player = new Player(Player.playerCounter*150, 50, 3);

        }
        else {
            player = new Player(400, 0, 3);
        }
        players.add(player);
        return player;
    }


}
