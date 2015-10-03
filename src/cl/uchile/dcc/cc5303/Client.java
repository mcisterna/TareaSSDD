package cl.uchile.dcc.cc5303;

import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by jose on 10/2/15.
 */
public class Client {

    static public void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
    //    boolean[] keys;
    //    keys = new boolean[KeyEvent.KEY_LAST];

        IGameEngine gameEngine = (IGameEngine) Naming.lookup(Server.url);
        IPlayer player  = gameEngine.joinGame();
        player.startJumping();
    }
}
