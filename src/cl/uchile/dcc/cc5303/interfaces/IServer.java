package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{

    public IPlayer joinGame() throws RemoteException;
    public IGame getGame() throws RemoteException;

}
