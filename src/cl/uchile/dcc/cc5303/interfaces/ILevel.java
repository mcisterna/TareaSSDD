package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ILevel extends Remote{

    List<IBench> getBenches() throws RemoteException;
}
