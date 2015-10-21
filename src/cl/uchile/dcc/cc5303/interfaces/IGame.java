package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGame extends Remote{

    List<IPlayer> getPlayers() throws RemoteException;
    List<ILevel> getLevels() throws RemoteException;
    List<IPlayer> getRanking() throws RemoteException;
}
