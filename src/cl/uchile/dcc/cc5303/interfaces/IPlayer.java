package cl.uchile.dcc.cc5303.interfaces;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends Remote{

    void startJumping() throws RemoteException;
    void startMovingRight() throws RemoteException;
    void startMovingLeft() throws RemoteException;
    void stopJumping() throws RemoteException;
    void stopMovingRight() throws RemoteException;
    void stopMovingLeft() throws RemoteException;
    int getLeft() throws RemoteException;
    int getRight() throws RemoteException;
    int getTop() throws RemoteException;
    int getWidth() throws RemoteException;
    int getHeight() throws RemoteException;
    Color getColor() throws RemoteException;
    int getLives() throws RemoteException;
    void setColor(Color color) throws RemoteException;
}
