package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cl.uchile.dcc.cc5303.Game;

public interface IServer extends Remote{

    IPlayer joinGame() throws RemoteException;
    IGame getGame() throws RemoteException;
    void playerIsReadyToContinue() throws RemoteException;
    double getLoad() throws RemoteException;
	void runNewGame(String[] args) throws RemoteException;
	void resumeGame(IGame game) throws RemoteException;
	void stopGame() throws RemoteException;
}
