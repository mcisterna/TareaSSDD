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

    public List<Player> players;
    public LinkedList<Level> levels;
    public boolean allTogether;
    public int maxPlayers;

    public Game() throws RemoteException {
        super();
        allTogether = false;
        maxPlayers = 4;
        this.players = new LinkedList<>();
        levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
            Level l = new Level(2);
            levels.add(l);
        }
    }

    public Game(boolean allTogether, int maxPlayers) throws RemoteException {
        super();
        this.allTogether = allTogether;
        this.maxPlayers = maxPlayers;
        this.players = new LinkedList<>();
        levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
            Level l = new Level(2);
            levels.add(l);
        }
    }

    public boolean isAllTogether() {
        return allTogether;
    }

    public void moveStageDown() {
        levels.add(new Level(2));
        for(Level l : levels) l.moveDown();
        levels.remove();

        for (Player player : players) {
            player.setPosY(player.getTop() + 100);
        }
    }

    public List<IPlayer> getPlayers() throws RemoteException{

        LinkedList<IPlayer> iplayers = new LinkedList<>();
        for(IPlayer player : players) {
            iplayers.add(player);
        }

        return iplayers;
    }

    public List<Player> getLocalPlayers() {
        return players;
    }


    public List<Level> getLevels() throws RemoteException{
        return levels;
    }

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }
}
