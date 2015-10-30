package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cl.uchile.dcc.cc5303.Game;

public interface IServer extends Remote{

    IPlayer joinGame() throws RemoteException;
    IGame getGame() throws RemoteException;
    Game getGame2() throws RemoteException;
    void playerIsReadyToContinue() throws RemoteException;
    double getLoad() throws RemoteException;
	void runNewGame(String[] args) throws RemoteException;
	void resumeGame(Game game) throws RemoteException;
	void stopGame() throws RemoteException;
}
