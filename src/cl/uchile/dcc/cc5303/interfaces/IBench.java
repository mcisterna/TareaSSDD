package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBench extends Remote{
    int getLeft() throws RemoteException;
    int getRight() throws RemoteException;
    int getTop() throws RemoteException;
    int getBottom() throws RemoteException;
    int getWidth() throws RemoteException;
    int getHeight() throws RemoteException;
}
