package cl.uchile.dcc.cc5303.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jose on 10/28/15.
 */
public interface IServersManager extends Remote{

	String getCurrentServerIp() throws RemoteException;
	void addServer(IServer server) throws RemoteException;
}
