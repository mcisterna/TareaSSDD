package cl.uchile.dcc.cc5303.interfaces;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jose on 10/2/15.
 */
public interface IPlayer extends Remote{

    public void startJumping() throws RemoteException;
    public void startMovingRight() throws RemoteException;
    public void startMovingLeft() throws RemoteException;
    public void stopJumping() throws RemoteException;
    public void stopMovingRight() throws RemoteException;
    public void stopMovingLeft() throws RemoteException;
    public Color getColor() throws RemoteException;
    public int getPosX() throws RemoteException;
    public int getPosY() throws RemoteException;
    public int getHeight() throws RemoteException;
    public int getWidth() throws RemoteException;
}
