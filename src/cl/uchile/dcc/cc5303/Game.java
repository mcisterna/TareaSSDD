package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class Game extends UnicastRemoteObject implements IGame {

    public List<IPlayer> players;
    public LinkedList<Level> levels;

    public Game(List<Player> players) throws RemoteException {
        super();
        this.players = new LinkedList<>();
        for(IPlayer player : players) {
            this.players.add(player);
        }
    }

    public void levelsDown() {
        levels.add(new Level(2));
        for(Level l : levels) l.moveDown();
        levels.remove();
    }

    public List<IPlayer> getPlayers() throws RemoteException{
        return players;
    }

    public List<Level> getLevels() throws RemoteException{
        return levels;
    }
}
