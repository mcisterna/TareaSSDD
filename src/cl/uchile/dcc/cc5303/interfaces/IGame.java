package cl.uchile.dcc.cc5303.interfaces;

import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGame extends Remote{

    public List<IPlayer> getPlayers() throws RemoteException;
    public List<Level> getLevels() throws RemoteException;
}
