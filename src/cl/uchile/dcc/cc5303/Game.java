package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.ILevel;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class Game extends UnicastRemoteObject implements IGame {

	public List<Player> players;
    public LinkedList<Level> levels;
    public int numPlayers;
    public boolean allTogether;
    public int maxPlayers;
    List<Player> ranking;

    public Game() throws RemoteException {
        super();
        allTogether = false;
        maxPlayers = 4;
        numPlayers = 0;
        this.players = new LinkedList<Player>();
        this.ranking = new LinkedList<Player>();
        levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
            Level l = new Level();
            levels.add(l);
        }
    }
    

    public Game(int maxPlayers) throws RemoteException {
        super();
        this.allTogether = true;
        this.maxPlayers = maxPlayers;
        this.numPlayers = 0;
        this.players = new LinkedList<Player>();
        this.ranking = new LinkedList<Player>();
        this.levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
            Level l = new Level();
            levels.add(l);
        }
    }

    public void restart() throws RemoteException {
        this.players = new LinkedList<Player>();
        this.ranking = new LinkedList<Player>();
        this.levels = new LinkedList<Level>();
        this.numPlayers = 0;
        Level.staticId = 0;
        for (int i = 0; i < 6; i++) {
            Level l = new Level();
            levels.add(l);
        }

    }

    public boolean isAllTogether() {
        return allTogether;
    }

    public void moveStageDown() throws RemoteException {
        levels.add(new Level());
        for(Level l : levels) l.moveDown();
        levels.remove();

        for (Player player : players) {
            player.setPosY(player.getTop() + 100);
        }
    }

    public List<IPlayer> getPlayers() throws RemoteException{

        LinkedList<IPlayer> iplayers;
        iplayers = new LinkedList<IPlayer>();
        for(IPlayer player : players) {
            iplayers.add(player);
        }

        return iplayers;
    }

    public List<Player> getLocalPlayers() {
        return players;
    }


    public List<Level> getLocalLevels() throws RemoteException{
        return levels;
    }

    public List<ILevel> getLevels() throws RemoteException{

        LinkedList<ILevel> ilevels;
        ilevels = new LinkedList<ILevel>();
        for(ILevel level : levels) {
            ilevels.add(level);
        }

        return ilevels;
    }
    
    public List<Player> getLocalRanking(){
    	return ranking;
    }

    public Player addPlayer() throws RemoteException {

        Player newPlayer = isAllTogether() || numPlayers < 2 ?
                new Player(100 + numPlayers*150, 50, 3) : new Player(400, 220, 3);

        players.add(newPlayer);
        this.numPlayers++;
        return newPlayer;
    }
    

	@Override
	public List<IPlayer> getRanking() throws RemoteException {
		LinkedList<IPlayer> iplayers;
        iplayers = new LinkedList<IPlayer>();
        for(IPlayer player : ranking) {
            iplayers.add(player);
        }

        return iplayers;
	}


	@Override
	public int getNumPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		return numPlayers;
	}


	@Override
	public boolean getAllTogether() throws RemoteException {
		// TODO Auto-generated method stub
		return allTogether;
	}


	@Override
	public int getMaxPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		return maxPlayers;
	}


	@Override
	public IPlayer getPlayerById(int id) throws RemoteException {
		for(Player p : players){
			if(p.getId() == id)
				return p;
		}
		return null;
	}
}
