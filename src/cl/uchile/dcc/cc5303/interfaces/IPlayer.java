package cl.uchile.dcc.cc5303.interfaces;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends Remote{

    public void startJumping() throws RemoteException;
    public void startMovingRight() throws RemoteException;
    public void startMovingLeft() throws RemoteException;
    public void stopJumping() throws RemoteException;
    public void stopMovingRight() throws RemoteException;
    public void stopMovingLeft() throws RemoteException;
    public int getLeft() throws RemoteException;
    public int getRight() throws RemoteException;
    public int getTop() throws RemoteException;
    public int getWidth() throws RemoteException;
    public int getHeight() throws RemoteException;
    public Color getColor() throws RemoteException;
}
