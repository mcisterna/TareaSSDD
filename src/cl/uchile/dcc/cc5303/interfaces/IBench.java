package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBench extends Remote{
    public int getLeft() throws RemoteException;
    public int getRight() throws RemoteException;
    public int getTop() throws RemoteException;
    public int getBottom() throws RemoteException;
    public int getWidth() throws RemoteException;
    public int getHeight() throws RemoteException;
}
