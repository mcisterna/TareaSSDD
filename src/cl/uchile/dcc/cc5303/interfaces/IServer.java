package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{

    IPlayer joinGame() throws RemoteException;
    IGame getGame() throws RemoteException;
    void playerIsReadyToContinue() throws RemoteException;
    double getLoad() throws RemoteException;
}
