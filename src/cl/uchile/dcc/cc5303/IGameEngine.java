package cl.uchile.dcc.cc5303;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jose on 10/2/15.
 */
public interface IGameEngine extends Remote{

    public IPlayer joinGame() throws RemoteException;



}
