package cl.uchile.dcc.cc5303.interfaces;

import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jose on 10/2/15.
 */
public interface IServer extends Remote{

    public IPlayer joinGame() throws RemoteException;
    public IGame getGame() throws RemoteException;

}
