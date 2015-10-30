package cl.uchile.dcc.cc5303.interfaces;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

import cl.uchile.dcc.cc5303.elements.Player;

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
    

	int getPlayerCounter() throws RemoteException;
	void setPlayerCounter(int playerCounter)  throws RemoteException;
	boolean isStandingUp()  throws RemoteException;
	void setStandingUp(boolean isStandingUp) throws RemoteException;
	boolean isWantsToMoveRight() throws RemoteException;
	void setWantsToMoveRight(boolean wantsToMoveRight) throws RemoteException;
	boolean isWantsToMoveLeft()  throws RemoteException;
	void setWantsToMoveLeft(boolean wantsToMoveLeft) throws RemoteException;
	boolean isWantsToJump()  throws RemoteException;
	void setWantsToJump(boolean wantsToJump) throws RemoteException;
	double getSpeed()  throws RemoteException;
	void setLives(int lives) throws RemoteException;
	
	int getId() throws RemoteException;

	void setId(int id) throws RemoteException;

}
